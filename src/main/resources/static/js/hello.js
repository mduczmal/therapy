const React = require('react');
const ReactDOM = require('react-dom');
class Hello extends React.Component {
    render() {
        return (
        <nav className="navbar navbar-dark bg-success">
            <a className="navbar-brand">Terapia</a>
            <p className="btn btn-success btn-outline-light mx-auto">Hello, React!</p>
        </nav>
    )
    }
}
ReactDOM.render(
<Hello />,
    document.getElementById('react')
);
