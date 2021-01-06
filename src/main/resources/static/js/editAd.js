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

export class EditAd extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            imagePreview: null,
            image: null,
            data: null,
            loaded: false,
            id: null
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleImageChange = this.handleImageChange.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
        this.getAd = this.getAd.bind(this);
        this.getId = this.getId.bind(this);
    }
    getId() {
        return this.state.data === null ? this.props.id : this.state.id;
    }

    getAd() {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/ad?id=" + this.getId(),
            {
                method: 'GET',
                headers: {
                    'X-XSRF-TOKEN': token
                }
            }).then(res => res.json())
            .then(
                (result) => {
                    console.log(result);
                    this.setState({
                        data: result.details,
                        id: result.id,
                        loaded: true
                    });
                },
                (error) => {
                    console.log(error);
                });
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
            fetch("/v2/ad?id=" + this.state.id,
                {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-XSRF-TOKEN': token
                    },
                    body: JSON.stringify(this.state.data, (k, v) => v === null ? "" : v)
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

    componentDidMount() {
        this.getAd();
    }

    render() {
        const selfLabels = {
            "edit": "Zapisz zmiany",
            "cancel": "Anuluj"
        }
        return (
            <React.Fragment>
                {!this.state.loaded ? null : (
                    <form noValidate autoComplete="off" onSubmit={this.handleSubmit}>
                        <Grid container spacing={1} alignItems={'center'}>
                            <Grid item xs={12}>
                                <Section title={this.props.labels.personalInfo}/>
                            </Grid>
                            <Grid item xs={3}>
                                <Grid container justify={'center'} alignItems={'center'}>
                                    <Box mb={2}>
                                        <Avatar className={this.props.avatar}
                                                src={this.state.imagePreview === null?
                                                    "/images/" + this.state.data.imageId
                                                    : this.state.imagePreview }/>
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
                                           defaultValue={this.state.data.name} onChange={this.handleChange}/>
                            </Grid>
                            <Grid item xs={6}>
                                <TextField id="surname" label={this.props.labels.surname} variant="filled" name="surname"
                                           defaultValue={this.state.data.surname} onChange={this.handleChange}/>
                            </Grid>
                            <Grid item xs={6}>
                                <TextField id="email" label={this.props.labels.email} variant="filled" name="email"
                                           defaultValue={this.state.data.email}  onChange={this.handleChange}/>
                            </Grid>
                            <Grid item xs={6}>
                                <TextField id="telephoneNumber" label={this.props.labels.telephoneNumber} variant="filled"
                                           defaultValue={this.state.data.telephoneNumber}  name="telephoneNumber" onChange={this.handleChange}/>
                            </Grid>
                            <Grid item xs={12}>
                                <Section title={this.props.labels.therapyInfo}/>
                            </Grid>
                            <Grid item xs={6}>
                                <TextField id="address" label={this.props.labels.address} variant="filled" name="address"
                                           defaultValue={this.state.data.address} onChange={this.handleChange}/>
                            </Grid>
                            <Grid item xs={6}>
                                <TextField id="therapyApproach" label={this.props.labels.therapyApproach} variant="filled"
                                           defaultValue={this.state.data.therapyApproach} name="therapyApproach" onChange={this.handleChange}/>
                            </Grid>
                            <Grid item xs={6}>
                                <FormControlLabel
                                    control={<Checkbox checked={this.state.data.onlineSessions} onChange={this.handleCheckboxChange}
                                                       name="onlineSessions" color={this.props.checkbox.color}/>}
                                    label={this.props.labels.onlineSessions}
                                />
                            </Grid>
                            <Grid item xs={6}>
                                <FormControlLabel
                                    control={<Checkbox checked={this.state.data.supervision} onChange={this.handleCheckboxChange}
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
                            <Grid item xs={6}>
                                <Button type="button" color={this.props.button.color}
                                        href={"/v2/ads"}>{selfLabels.cancel}</Button>
                            </Grid>
                            <Grid item xs={6}>
                                {/*TODO: redirect to home*/}
                                <Button type="submit" color={this.props.button.color}>{selfLabels.edit}</Button>
                            </Grid>
                        </Grid>
                    </form>
                )}
            </React.Fragment>
        )
    }
}

