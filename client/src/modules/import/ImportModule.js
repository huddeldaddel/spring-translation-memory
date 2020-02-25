import React, {Component} from 'react';
import './ImportModule.css';
import { Button } from 'react-bootstrap';
import HistoryDisplay from "../common/HistoryDisplay";

class ImportModule extends Component {

    constructor(props) {
        super(props);
        this.state = {
            history: JSON.parse(localStorage.getItem("import-history") || "[]")
        };
    }

    handleFormSubmit = (evt) => {
        const file = this.fileInput.value;
        if(!file) {
            evt.preventDefault();
        } else {
            const fileName = file.match(/.*[\\\/](.*)$/)[1];                                                     // extract filename from fake path
            const newHistory = this.state.history.concat([fileName]);
            this.setState({ history: newHistory });
            localStorage.setItem("import-history", JSON.stringify(newHistory));
        }
    };

    /**
     * The implementation of the upload form contains a work-around. I wasn't able to find a way to make fetch() fire a
     * valid multipart/form-data request with named part for the upload file (as it's required by the Spring backend).
     * Instead a plain old form is used to upload the file. By default the browser would then display the response to
     * that request. The server returns a JSON  document - which we don't want to display. Therefore I redirect the
     * response to a hidden iframe.
     */
    render() {
        return (
            <div>
                <form action="http://localhost:8080/translations/"
                      method="post"
                      enctype="multipart/form-data"
                      onSubmit={ this.handleFormSubmit }
                      target="hiddenFrame">
                    <label>Upload file
                        <input type="file" accept="application/json" name="file" ref={ref=> this.fileInput = ref}></input>
                    </label>
                    <Button type="submit" size="sm">Upload</Button>
                </form>
                <iframe title="hiddenTarget" name="hiddenFrame" style={{display: "none"}}></iframe>
                <HistoryDisplay history={this.state.history} name="Upload history" />
            </div>
        );
    }

}

export default ImportModule;