import React from 'react';
import ReactDOM from "react-dom";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import {Hello} from "./hello";
import {AdDetails} from "./addetails";

export function App() {
    return (
        <div>
            <Switch>
                <Route path="/hello">
                    <Hello />
                </Route>
                <Route path="/v2/details">
                    <AdDetails />
                </Route>
            </Switch>
        </div>
    );
}

ReactDOM.render(
    <Router>
        <App/>
    </Router>,
    document.getElementById('react')
);
