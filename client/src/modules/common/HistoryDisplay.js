import React from 'react';
import './HistoryDisplay.css';
import {Table} from "react-bootstrap";

function HistoryDisplay(props) {
    return 0 === props.history.length ? (<p>No entries found</p>) : (
        <Table striped bordered hover size="sm">
            <thead>
                <tr>
                    <th>{ props.name }</th>
                </tr>
            </thead>
            <tbody>
                { props.history.map((item, index) =>
                    <tr key={ index }>
                        <td>{ item }</td>
                    </tr>
                )}
            </tbody>
        </Table>
    );
}

export default HistoryDisplay;