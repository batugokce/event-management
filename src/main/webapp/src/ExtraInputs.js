import React, { Component } from 'react'
import InputGroup from 'react-bootstrap/InputGroup'
import FormControl from  'react-bootstrap/FormControl'
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from "react-bootstrap/Form";


class ExtraInputs extends Component {
    constructor(props){
        super(props);
        this.state = {
            ext1Checked : false,
            ext2Checked : false,
            ext3Checked : false,
        }
    }

    handle1 = () => {
        this.setState({
            ext1Checked: !this.state.ext1Checked,
            ext2Checked: false,
            ext3Checked: false
        })
        this.props.resetExt1();
        this.props.resetExt2();
        this.props.resetExt3();
    }
    handle2 = () => {
        this.setState({
            ext2Checked: !this.state.ext2Checked,
            ext3Checked: false
        })
        this.props.resetExt2();
        this.props.resetExt3();
    }
    handle3 = () => {
        this.setState({ext3Checked: !this.state.ext3Checked})
        this.props.resetExt3();
    }

    
    render() {
        return (
            <div>
                <Form.Group as={Row} className="mb-3">
                        <Form.Label column sm={3}>
                        Katılımcılara Ek Soru
                        </Form.Label>
                        <Col sm={8}>
                        <InputGroup >
                            <InputGroup.Prepend  >
                                <InputGroup.Checkbox checked={this.state.ext1Checked} 
                                                    onChange={this.handle1} />
                            </InputGroup.Prepend>
                            <FormControl value={this.props.extra1} onChange={this.props.onChange1} 
                                        disabled={!this.state.ext1Checked}  />
                            </InputGroup>
                        </Col>
                </Form.Group>

                <Form.Group as={Row} className="mb-3">
                        <Form.Label column sm={3}>
                        Katılımcılara Ek Soru
                        </Form.Label>
                        <Col sm={8}>
                        <InputGroup >
                            <InputGroup.Prepend  >
                                <InputGroup.Checkbox checked={this.state.ext2Checked} disabled={!this.state.ext1Checked}
                                                    onChange={this.handle2} />
                            </InputGroup.Prepend>
                            <FormControl value={this.props.extra2} onChange={this.props.onChange2} 
                                        disabled={!this.state.ext2Checked}  />
                            </InputGroup>
                        </Col>
                </Form.Group>

                <Form.Group as={Row} className="mb-3">
                        <Form.Label column sm={3}>
                        Katılımcılara Ek Soru
                        </Form.Label>
                        <Col sm={8}>
                        <InputGroup >
                            <InputGroup.Prepend  >
                                <InputGroup.Checkbox checked={this.state.ext3Checked} disabled={!this.state.ext2Checked}
                                                    onChange={this.handle3} />
                            </InputGroup.Prepend>
                            <FormControl value={this.props.extra3} onChange={this.props.onChange3} 
                                        disabled={!this.state.ext3Checked}  />
                            </InputGroup>
                        </Col>
                </Form.Group>
            </div>
        )
    }
}

export default ExtraInputs;