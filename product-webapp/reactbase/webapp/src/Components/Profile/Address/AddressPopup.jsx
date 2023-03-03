import React, { useEffect, useState } from 'react';
import Form from 'react-bootstrap/Form';
import { GoPlus } from 'react-icons/go'
import axios from 'axios';
import { Col, Container, Row } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { TextField } from '@mui/material';

import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { AiOutlineClose } from 'react-icons/ai';
import { Base_Url } from '../../BaseUrl';



export const AddressPopup = (props) => {
    const [open, setOpen] = useState(false);

    const handleClose = () => setOpen(false);
    const [addressData, setAddressData] = useState();

    const [addressdetails, setAddressdetails] = useState();
    const {
        register,
        handleSubmit,
        formState: { errors },
        reset,
        trigger,
    } = useForm();

    const handleClickOpen = () => {
        reset()
        setAddressData('');
        setAddressdetails('')
        setOpen(true);
    };

    useEffect(() => {
        props.handleedit ? setOpen(true) : setOpen(false);
        setAddressdetails(props.handleedit);
        setAddressData(props.handleedit);
    }, [props.handleedit,props.email])


    const addAddress = () => {
        const addressId = Math.floor(100000 + Math.random() * 900000);
        const addressdetailsVal = {
            addressId: addressId,
            houseNumber: addressdetails.houseNumber,
            city: addressdetails.city,
            state: addressdetails.state,
            colony: addressdetails.colony,
            pincode: addressdetails.pincode
        }
        axios.post(`${Base_Url}/user-service/api/v1/addAddress/${props.email}`, addressdetailsVal, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(res => {
            handleClose();
            props.handlepopup('savedata')

        })
    }

    const updateAddress = () => {
        axios.put(`${Base_Url}/user-service/api/v1/updateAddress/${props.email}`, addressdetails, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(res => {
            props.handlepopup('savedata');
            handleClose();
        })
    }
    const handleInput = (e) => {
        setAddressdetails({ ...addressdetails, [e.target.name]: e.target.value });
    }


    return (
        <>
            <div>
                <div className="d-flex justify-content-end" >
                    <Button variant="outlined" onClick={handleClickOpen} style={{ fontSize: "1.3em", marginRight: "9.5rem" }} className="btn">
                        <GoPlus /> Add New
                    </Button>
                </div>

                <Dialog
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="alert-dialog-title"
                    aria-describedby="alert-dialog-description"
                    fullWidth maxWidth="md"
                >
                    <div className='close-button'>
                        <AiOutlineClose onClick={handleClose} />
                    </div>
                    <DialogTitle id="alert-dialog-title" className='mb-3 d-flex justify-content-center dialog-title p-0' style={{ color: "#850E35", fontSize: "bold" }}>
                        {addressData ? "Update Address" : "New Address"}
                    </DialogTitle>
                    <hr />
                    <DialogContent className='pt-3'>
                        <Form onSubmit={addressData ? handleSubmit(updateAddress) : handleSubmit(addAddress)}>
                            <Container>
                                <Row>
                                    <Col sm={6}>
                                        <TextField
                                            id="demo-helper-text-misaligned"
                                            helperText={errors.houseNumber && (
                                                <small className="text-danger">{errors.houseNumber.message}</small>
                                            )}
                                            className="mb-3"
                                            fullWidth
                                            label="House Number"
                                            type="text"
                                            name="houseNumber"
                                            {...register("houseNumber", {
                                                required: {
                                                    value: addressdetails?.houseNumber ? false : true,
                                                    message: " houseNumber is Required"
                                                }
                                            })}

                                            onChange={handleInput}
                                            onKeyUp={() => {
                                                trigger("houseNumber");
                                            }}
                                            error={errors.houseNumber}
                                            value={addressdetails?.houseNumber}




                                        />
                                    </Col>
                                    <Col sm={6}>
                                        <TextField
                                            id="demo-helper-text-misaligned"
                                            helperText={errors.colony && (
                                                <small className="text-danger">{errors.colony.message}</small>
                                            )}
                                            className="mb-3"
                                            fullWidth
                                            label="Colony"
                                            type="text"
                                            name="colony"

                                            {...register("colony", {
                                                required: {
                                                    value: addressdetails?.colony ? false : true,
                                                    message: " Colony is Required"
                                                }
                                            })}

                                            error={errors.colony}

                                            onKeyUp={() => {
                                                trigger("colony");
                                            }}
                                            value={addressdetails?.colony}
                                            onChange={handleInput}


                                        />
                                    </Col>
                                </Row>
                                <Row>

                                    <Col sm={6}><TextField
                                        id="demo-helper-text-misaligned"
                                        className="mb-3"
                                        helperText={errors.city && (
                                            <small className="text-danger">{errors.city.message}</small>
                                        )}

                                        fullWidth
                                        label="City"
                                        type="text"
                                        {...register("city", {
                                            required: {
                                                value: addressdetails?.city ? false : true,
                                                message: "City is Required"
                                            }
                                        })}
                                        error={errors.city}

                                        onKeyUp={() => {
                                            trigger("city");
                                        }}
                                        value={addressdetails?.city}
                                        onChange={handleInput}

                                    /></Col>
                                    <Col sm={6}>
                                        <TextField
                                            id="demo-helper-text-misaligned" className="mb-3"
                                            fullWidth
                                            label="State"
                                            name="state"
                                            {...register("state", {
                                                required: {
                                                    value: addressdetails?.state ? false : true,
                                                    message: " State is required"
                                                }
                                            })}
                                            helperText={errors.state && (
                                                <small className="text-danger">{errors.state.message}</small>
                                            )}


                                            error={errors.state}
                                            value={addressdetails?.state}
                                            onChange={handleInput}

                                            onKeyUp={() => {
                                                trigger("state");
                                            }}

                                        />

                                    </Col>
                                </Row>
                                <Row>

                                    <Col sm={6}><TextField
                                        id="demo-helper-text-misaligned" className="mb-3"
                                        fullWidth
                                        label="Pincode"
                                        name="pincode"
                                        type="text"
                                        {...register("pincode", {
                                            required: "pincode is Required",
                                            minLength: {
                                                value: 6,
                                                message: "pincode will 6 digit",
                                            },
                                            pattern: {
                                                value: /^[1-9]{1}[0-9]{2}\s{0,1}[0-9]{3}$/,
                                                message: "Enter valid pincode"
                                            }

                                        })}
                                        helperText={errors.pincode && (
                                            <small className="text-danger">{errors.pincode.message}</small>
                                        )}
                                        onKeyUp={() => {
                                            trigger("pincode");
                                        }}
                                        value={addressdetails?.pincode}
                                        onChange={handleInput}


                                        error={errors.pincode}

                                    /></Col>
                                    <Col sm={6}></Col>
                                </Row>
                                <Row>
                                    <Col sm={6}>

                                    </Col>
                                    <Col sm={6} className="d-flex justify-content-end" >
                                        <Button type="submit" style={{ fontSize: '1.5rem' }} className="btn">{addressData ? "update" : "save"}</Button>
                                    </Col>
                                </Row>

                            </Container>


                        </Form>
                    </DialogContent>

                </Dialog>
            </div>
        </>
    );
}