import React from 'react';
import './TabbedDisplay.css';
import {Tab, Tabs} from "react-bootstrap";
import HistoryDisplay from "../common/HistoryDisplay";
import ResultDisplay from "./ResultDisplay";

function TabbedDisplay(props) {
    return (
        <Tabs defaultActiveKey="results" id="uncontrolled-tab-example">
            <Tab eventKey="results" title="Search Results">
                <ResultDisplay results={props.results} />
            </Tab>
            <Tab eventKey="history" title="History">
                <HistoryDisplay history={props.history} name="Search history" />
            </Tab>
        </Tabs>
    );
}

export default TabbedDisplay;