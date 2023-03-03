import { TextField } from "@mui/material"
import axios from "axios";
import { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useForm } from "react-hook-form";
import { Box, Grid } from '@mui/material';
import { Base_Url } from "../../BaseUrl";

export const ProfileInfo = (props) => {
    const [info, setInfo] = useState([]);

    useEffect(() => {
        if (props.email) {
            props.dataupdated ? setInfo(props.dataupdated) :
                axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
                    "Content-Type": 'application/json',
                    'Access-Control-Allow-Origin': '*',
                    'Authorization':  localStorage.getItem('token') 
                  }}).then(res => {
                    setInfo(res.data)
                })
        }

    }, [props, props.profilechange, props.email])

    const {
        register,
        handleSubmit,
        formState: { errors },
        trigger,
    } = useForm();

    const onSubmit = () => {
        axios.put(`${Base_Url}/user-service/api/v1/updateUser`, info, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(res => {
            props.savedDetails('passed data')
        })
    }
    const handleChange = (e) => {

        setInfo({ ...info, [e.target.name]: e.target.value });
    }
    return (
        <Form onSubmit={handleSubmit(onSubmit)}>


            <Box sx={{ flexGrow: 1}}>
                <Grid container spacing={2} columns={16}>
                    <Grid item xs={12} md={8} sm={8} sx={{ boxShadow: "none" }}>
                         <TextField
                            id="demo-helper-text-misaligned"
                            className="mb-3"
                            fullWidth
                            label="Title"
                            value="Mr./Mrs."
                            disabled
                        />
                    </Grid>
                    <Grid item xs={12} md={8} sm={8}>
                        
                            <TextField
                                id="demo-helper-text-misaligned"
                                className="mb-3"
                                helperText={errors?.name && (
                                    <small className="text-danger">{errors?.name.message}</small>
                                )}
                                fullWidth
                                label="Your Name"
                                name="name"
                                {...register("name", {
                                    required: {
                                        value: info?.name ? false : true,
                                        message: " Your Name is Required"
                                    }
                                })}
                                onKeyUp={() => {
                                    trigger("name");
                                }}
                                onChange={handleChange}
                                error={errors.name}
                                value={info?.name}
                            />
                        
                    </Grid>
                    <Grid item xs={12} md={8} sm={8}>
                        
                            <TextField
                                id="demo-helper-text-misaligned" className="mb-3"
                                fullWidth
                                label="Email"
                                name="email"

                                helperText={errors.email && (
                                    <small className="text-danger">{errors.email.message}</small>
                                )}
                                {...register("email", {
                                    required: {
                                        value: info?.email ? false : true,
                                        message: " Email is Required",
                                    },
                                    pattern: {
                                        value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                        message: "Invalid email address",
                                    }
                                })}
                                onKeyUp={() => {
                                    trigger("email");
                                }}
                                onChange={handleChange}
                                error={errors.email}
                                value={info?.email}
                                disabled

                            />
                    
                    </Grid>
                    <Grid item xs={12} md={8} sm={8}>
                        
                            <TextField
                                id="demo-helper-text-misaligned" className="mb-3"
                                fullWidth
                                label="Phone Number"
                                name="mobileNumber"

                                helperText={errors.mobileNumber && (
                                    <small className="text-danger">{errors.mobileNumber.message}</small>
                                )}
                                {...register("mobileNumber", {
                                    required: {
                                        value: info?.mobileNumber ? false : true,
                                        message: " Phone Number is Required"
                                    },
                                    minLength: {
                                        value: 10,
                                        message: "Minimum Phone 10 digit",
                                    },
                                    pattern: {
                                        value: /^\s*(?:\+?(\d{1,3}))?[-. (]*(\d{3})[-. )]*(\d{3})[-. ]*(\d{4})(?: *x(\d+))?\s*$/,
                                        message: "Invalid phone no",
                                    },

                                })}
                                onKeyUp={() => {
                                    trigger("mobileNumber");
                                }}
                                onChange={handleChange}
                                error={errors.mobileNumber}
                                value={info?.mobileNumber}
                            />
                        
                    </Grid>
                    <Grid item xs={12} md={8} sm={8}>

                    </Grid>
                    <Grid item xs={12} md={8} sm={8} className="d-flex justify-content-end">
                    <Button type="submit" style={{  fontSize: '1.5rem', color: "#850E35", display:"block" }} className="outlined">Save</Button>
                    </Grid>
                </Grid>
            </Box>
            


        </Form>
    )
}