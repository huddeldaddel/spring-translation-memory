import React, {Component} from 'react';
import './App.css';
import { Button } from 'react-bootstrap';
import SearchModule from "./modules/search/SearchModule";
import ImportModule from "./modules/import/ImportModule";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = { activeModule: 'search' };
    }

    selectHomeModule = () => {
        this.setState({ activeModule: 'search' });
    }

    selectSearchModule = () => {
        this.setState({ activeModule: 'import' });
    }

    render() {
        const modules = {
            search: <SearchModule />,
            import: <ImportModule />
        };

        return (
            <div className="app">
                <div className="app-header">
                    <Button variant="link" onClick={ this.selectHomeModule }>Home</Button> |
                    <Button variant="link" onClick={ this.selectSearchModule }>Import</Button>
                </div>
                <div className="container-fluid">
                    { modules[this.state.activeModule] }
                </div>
            </div>
        );
    }

}

export default App;
