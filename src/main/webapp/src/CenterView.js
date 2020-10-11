import React from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

export default class CenterView extends React.Component {
    render() {
        return (
            <Container>
                <Row className="show-grid">
                    <Col sm={2}> </Col> <Col sm={8}> {this.props.children} </Col>{" "}
                    <Col sm={2}> </Col>{" "}
                </Row>{" "}
            </Container>
        );
    }
}