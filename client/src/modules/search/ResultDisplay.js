import React from 'react';
import './ResultDisplay.css';
import {Table} from "react-bootstrap";

function ResultDisplay(props) {
    return 0 === props.results.length ? (<p>No results found</p>) : (
        <Table striped bordered hover size="sm">
            <thead>
            <tr>
                <th>Source</th>
                <th>Target</th>
            </tr>
            </thead>
            <tbody>
            { props.results.map((item, index) =>
                <tr key={ index }>
                    <td>{ item.source }</td>
                    <td>{ item.target }</td>
                </tr>
            )}
            </tbody>
        </Table>
    );
}

export default ResultDisplay;