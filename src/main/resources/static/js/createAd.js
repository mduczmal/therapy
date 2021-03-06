import React from "react";
import {getCookie} from "./hello";
import {Pricing} from "./pricing";
import {Section} from "./section";
import {Box, Button, Grid, TextField} from "@material-ui/core";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import AddPhotoAlternate from "@material-ui/icons/AddPhotoAlternate";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import {Redirect} from 'react-router-dom';

export class CreateAd extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            num: 0,
            data: {
                name: '',
                surname: '',
                address: '',
                description: '',
                pricing: {},
                therapyCenter: '',
                imageId: '',
                telephoneNumber: '',
                email: '',
                therapyApproach: '',
                training: '',
                supervision: false,
                onlineSessions: false
            },
            image: null
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleImageChange = this.handleImageChange.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
    }

    handleChange(event) {
        const name = event.target.name;
        this.setState({
            ...this.state,
            data: {
                ...this.state.data,
                [name]: event.target.value
            }
        });
    }

    handleCheckboxChange(event) {
        const name = event.target.name;
        this.setState({
            ...this.state,
            data: {
                ...this.state.data,
                [name]: event.target.checked
            }
        });
    }

    handleSubmit(event) {
        var formData = new FormData();
        formData.append('image', this.state.image);
        const token = getCookie('XSRF-TOKEN');
        fetch("/upload",
            {
                method: 'POST',
                headers: {
                    'X-XSRF-TOKEN': token
                },
                body: formData
            }).then(res => res.json())
            .then(
                (result) => {
                    console.log(result);
                    this.setState({
                        data: {
                            ...this.state.data,
                            imageId: result.id
                        },
                    });
                },
                (error) => {
                    console.log(error);
                }
            ).then(() => {
            fetch("/v2/ad",
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-XSRF-TOKEN': token
                    },
                    body: JSON.stringify(this.state.data)
                })
                .then(res => res.json())
                .then(
                    (result) => {
                        this.setState({
                            redirect: true,
                        });
                        console.log(result);
                    },
                    (error) => {
                        console.log(error);
                    }
                )
        });
        event.preventDefault();
    }

    handleImageChange(event) {
        const file = event.target.files[0];
        this.setState({
            imagePreview: URL.createObjectURL(file),
            image: file
        })
        event.preventDefault();
    }

    render() {
        if (this.state.redirect) {
            return <Redirect to="/v2/ads"/>
        }
        return (
            <form noValidate autoComplete="off" onSubmit={this.handleSubmit}>
                <Grid container spacing={1} alignItems={'center'}>
                    <Grid item xs={12}>
                        <Section title={this.props.labels.personalInfo}/>
                    </Grid>
                    <Grid item xs={3}>
                        <Grid container justify={'center'} alignItems={'center'}>
                            <Box mb={2}>
                                <Avatar className={this.props.avatar} src={this.state.imagePreview}/>
                            </Box>
                        </Grid>
                    </Grid>
                    <Grid item xs={1}>
                        <input accept="image/*" style={{display: 'none'}} id="upload-button" type="file"
                               onChange={this.handleImageChange}/>
                        <label htmlFor="upload-button">
                            <IconButton color="primary" aria-label="upload picture" component="span">
                                <AddPhotoAlternate/>
                            </IconButton>
                        </label>
                    </Grid>
                    <Grid item xs={8}/>
                    <Grid item xs={6}>
                        <TextField id="name" label={this.props.labels.name} variant="filled" name="name"
                                   onChange={this.handleChange}/>
                    </Grid>
                    <Grid item xs={6}>
                        <TextField id="surname" label={this.props.labels.surname} variant="filled" name="surname"
                                   onChange={this.handleChange}/>
                    </Grid>
                    <Grid item xs={6}>
                        <TextField id="email" label={this.props.labels.email} variant="filled" name="email"
                                   onChange={this.handleChange}/>
                    </Grid>
                    <Grid item xs={6}>
                        <TextField id="telephoneNumber" label={this.props.labels.telephoneNumber} variant="filled"
                                   name="telephoneNumber" onChange={this.handleChange}/>
                    </Grid>
                    <Grid item xs={12}>
                        <Section title={this.props.labels.therapyInfo}/>
                    </Grid>
                    <Grid item xs={6}>
                        <TextField id="address" label={this.props.labels.address} variant="filled" name="address"
                                   onChange={this.handleChange}/>
                    </Grid>
                    <Grid item xs={6}>
                        <TextField id="therapyApproach" label={this.props.labels.therapyApproach} variant="filled"
                                   name="therapyApproach" onChange={this.handleChange}/>
                    </Grid>
                    <Grid item xs={6}>
                        <FormControlLabel
                            control={<Checkbox checked={this.state.onlineSessions} onChange={this.handleCheckboxChange}
                                               name="onlineSessions" color={this.props.checkbox.color}/>}
                            label={this.props.labels.onlineSessions}
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <FormControlLabel
                            control={<Checkbox checked={this.state.supervision} onChange={this.handleCheckboxChange}
                                               name="supervision" color={this.props.checkbox.color}/>}
                            label={this.props.labels.supervision}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Section title={this.props.labels.pricing}/>
                    </Grid>
                    <Grid item xs={12}>
                        <Pricing/>
                    </Grid>
                    <Grid item xs={6}/>
                    <Grid item xs={6}>
                        <Button type="submit" color={this.props.button.color}>{this.props.labels.submit}</Button>
                    </Grid>
                </Grid>
            </form>
        )
    }
}