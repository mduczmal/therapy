import React from "react";
import {getCookie} from "./hello";
import {Box, Button, Grid, TextField} from "@material-ui/core";
import FormLabel from "@material-ui/core/FormLabel";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import AddPhotoAlternate from "@material-ui/icons/AddPhotoAlternate";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";

export class CreateForm extends React.Component {
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
                imagePath: '',
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
        console.log("I am submitting");
        var formData = new FormData();
        formData.append('image', this.state.image);
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/upload",
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
                            imagePath: result.path
                        },
                    });
                    console.log(this.state.data);
                },
                (error) => {
                    console.log(error);
                }
            ).then(() => {
            fetch("http://localhost:8080/v2/ad",
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
                            isLoaded: true,
                        });
                        console.log(result);
                    },
                    (error) => {
                        console.log(error);
                    }
                )
        })
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
        return (
            <form noValidate autoComplete="off" onSubmit={this.handleSubmit}>
                <Grid container spacing={1} alignItems={'center'}>
                    <Grid item xs={12}>
                        <Box mt={4} mb={2} mx={4}>
                            <Grid container justify={'flex-start'} alignItems={'center'}>
                                <FormLabel component={'legend'}>{this.props.labels.personalInfo}</FormLabel>
                            </Grid>
                        </Box>
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
                    <Grid item xs={8}></Grid>
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
                        <Box mt={4} mb={2} mx={4}>
                            <Grid container justify={'flex-start'} alignItems={'center'}>
                                <FormLabel component={'legend'}>{this.props.labels.therapyInfo}</FormLabel>
                            </Grid>
                        </Box>
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
                    <Grid item xs={6}></Grid>
                    <Grid item xs={6}>
                        <Button type="submit" color={this.props.button.color}>{this.props.labels.submit}</Button>
                    </Grid>
                </Grid>
            </form>
        )
    }
}