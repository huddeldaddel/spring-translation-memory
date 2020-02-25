import React, {Component} from 'react';
import './SearchModule.css';
import { Button } from 'react-bootstrap';
import TabbedDisplay from "./TabbedDisplay";

class SearchModule extends Component {

    constructor(props) {
        super(props);
        this.state = {
            history: JSON.parse(localStorage.getItem("search-history") || "[]"),
            query: localStorage.getItem("search-query") || "",
            results: JSON.parse(localStorage.getItem("search-results") || "[]")
        };
    }

    handleFormSubmit = (evt) => {
        evt.preventDefault();
        this.performSearch();
    };

    handleQueryChanged = (evt) => {
        this.setState({ query: evt.target.value });
        localStorage.setItem("search-query", evt.target.value);
    };

    performSearch = () => {
        const newHistory = this.state.history.concat([this.state.query]);
        this.setState({ history: newHistory });
        localStorage.setItem("search-history", JSON.stringify(newHistory));
        fetch("http://localhost:8080/translations/" + encodeURI(this.state.query),
            {
                method: "GET",
                dataType: "JSON",
                headers: { "Content-Type": "application/json; charset=utf-8" }
            })
            .then((resp) => { return resp.json(); })
            .then((data) => {
                this.setState({ results: data.results });
                localStorage.setItem("search-results", JSON.stringify(data.results));
            })
            .catch((error) => {
                this.setState({ results: [] });
                localStorage.setItem("search-results", "[]");
                console.log(error);
            });
    };

    render() {
        return (
           <div>
               <form onSubmit={ this.handleFormSubmit }>
                   <label>Source
                       <input value={ this.state.query } onChange={ this.handleQueryChanged }></input>
                   </label>
                   <Button onClick={ this.performSearch } size="sm" disabled={ 0 === this.state.query.length}>Search</Button>
               </form>
               <TabbedDisplay history={ this.state.history } results={ this.state.results } />
           </div>
        );
    }

}

export default SearchModule;