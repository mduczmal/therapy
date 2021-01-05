import React from 'react';
import ReactDOM from "react-dom";
import {BrowserRouter as Router, Route, Switch, useParams} from "react-router-dom";
import {Hello} from "./hello";
import {Details} from "./details";
import {Create} from "./create";
import {Ads} from "./ads";

export function App() {
    function GetDetails() {
        let { id } = useParams();
        return <Details key={id} id={id}/>;
    }
    function GetEditAd() {
        let { id } = useParams();
        return <Create key={"edit/" + id} edit={true} id={id}/>;
    }

    return (
        <div>
            <Switch>
                <Route path="/hello">
                    <Hello/>
                </Route>
                <Route path="/v2/details/:id">
                    <GetDetails />
                </Route>
                <Route path="/v2/create">
                    <Create edit={false}/>
                </Route>
                <Route path="/v2/edit/:id">
                    <GetEditAd />
                </Route>
                <Route path="/v2/ads">
                    <Ads />
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
