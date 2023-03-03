import React, { useState, useEffect } from 'react';
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

export const ControlledPopup = (props) => {
    const [cardnew, setCardnew] = useState();
    const [cardDetails, setcardDetails] = useState();
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        reset()
        setCardnew('');
        setcardDetails('')
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };


    useEffect(() => {
        props.editPopup ? setOpen(true) : setOpen(false);
        if (props.editPopup) {
            reset()
            setCardnew(props.editPopup)
            setcardDetails(props.editPopup)
        }
    }, [props.editPopup])
    const addCard = (data) => {
        const carddetails = {
            name: data.name,
            cardNumber: data.cardNumber,
            expiryMonth: data.date.split('/')[0],
            expiryYear: data.date.split('/')[1]
        }
        axios.post(`${Base_Url}/user-service/api/v1/addcardDetails/${props.email}`, carddetails, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(res => {
            handleClose();
            props.handlepopup('savedata');
        })
    }
    const updateCard = (data) => {
        const carddetails = {
            name: data.name,
            cardNumber: data.cardNumber,
            expiryMonth: data.date.split('/')[0],
            expiryYear: data.date.split('/')[1]
        }
        axios.put(`${Base_Url}/user-service/api/v1/updateCardDetails/${props.email}`, carddetails, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(response => {
            props.handlepopup('savedata');
            setOpen(false)
        })
    }
    const handleInput = (e) => {
        setCardnew({ ...cardnew, [e.target.name]: e.target.value });
    }

    const {
        register,
        handleSubmit,
        formState: { errors },
        reset,
        trigger,
    } = useForm();
    return (
        <><div>
            <div className="d-flex justify-content-end" style={{ padding: "0rem 17.5rem 0rem 0rem" }}>
                <Button variant="outlined" onClick={handleClickOpen} style={{ fontSize: "1.3em" }} className="btn">
                    <GoPlus /> Add New
                </Button>
            </div>

            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                fullWidth maxWidth="sm"
            >
                <div className='close-button'>
                    <AiOutlineClose onClick={handleClose} />
                </div>
                <DialogTitle id="alert-dialog-title" className='mb-3 d-flex justify-content-center dialog-title p-0' style={{ color: "#850E35", fontSize: "bold" }}>
                    {cardDetails ? "Update Card" : "New Card"}
                </DialogTitle>
                <hr />

                <DialogContent className='pt-3'>
                    <Form onSubmit={cardDetails ? handleSubmit(updateCard) : handleSubmit(addCard)}>
                        <Container>
                            <Row>
                                <Col sm={6}>
                                    <TextField
                                        id="demo-helper-text-misaligned"
                                        helperText={errors.name && (
                                            <small className="text-danger">{errors.name.message}</small>
                                        )}
                                        className="mb-3"
                                        fullWidth
                                        label="Bank Name"
                                        type="text"
                                        name="name"

                                        {...register("name", {
                                            required: {
                                                value: cardnew?.name ? false : true,
                                                message: " Bank Name is Required"
                                            }
                                        })}
                                        error={errors.name}

                                        onKeyUp={() => {
                                            trigger("name");
                                        }}
                                        value={cardnew?.name}
                                        onChange={handleInput}

                                    />

                                </Col>
                                <Col sm={6}><TextField
                                    id="demo-helper-text-misaligned"
                                    helperText={errors.date && (
                                        <small className="text-danger">{errors.date.message}</small>
                                    )}
                                    className="mb-3"
                                    fullWidth
                                    label="Expiry Date(MM/YYYY)"
                                    type="text"
                                    name="date"                                   
                                    {...register("date", {
                                        required: {
                                            value: cardnew?.date ? false : true,
                                            message: "Expiry Date is Required"
                                        },
                                        pattern: {
                                            value: /^((0[1-9])|(1[0-2]))\/(20((1[1-9])|([2-9][0-9])))$/,
                                            message: "Please Enter a MM/YYYY"
                                        }
                                    })}                                                                      
                                    error={errors.date}
                                    
                                    onKeyUp={() => {
                                        trigger("date");
                                    }}
                                    value={cardnew?.date}
                                    onChange={handleInput}
                                    
                                    
                                /></Col>
                            </Row>
                            <Row>
                                <Col sm={12}><TextField
                                    id="demo-helper-text-misaligned" className="mb-3"
                                    fullWidth
                                    label="Card Number"
                                    name="cardNumber"
                                    {...register("cardNumber", {
                                        required: {
                                            value: cardnew?.cardNumber ? false : true,
                                            message: " Card Number is required"
                                        },
                                        minLength: {
                                            value: 16,
                                            message: "minimum 16 digit"
                                        },
                                        pattern: {
                                            value: /^[0-9]*$/,
                                            message: "Only Number Allowed"
                                        }
                                    })}
                                    helperText={errors.cardNumber && (
                                        <small className="text-danger">{errors.cardNumber.message}</small>
                                    )}

                                    onChange={handleInput}
                                    error={errors.cardNumber}
                                    value={cardnew?.cardNumber}
                                    onKeyUp={() => {
                                        trigger("cardNumber");
                                    }}

                                /></Col>
                                {/* <Col sm={6}>
                                    <TextField
                                    id="demo-helper-text-misaligned" className="mb-3"
                                    fullWidth
                                    label="CVV"
                                    name="cvv"
                                    type="text"
                                    {...register("cvv", {
                                        required: "CVV is Required",
                                        minLength: {
                                            value: 3,
                                            message: "CVV will 3 digit",
                                        },
                                        pattern: {
                                            value: /^[0-9]*$/,
                                            message: "Only Number Allowed"
                                        }

                                    })}
                                    helperText={errors.cvv && (
                                        <small className="text-danger">{errors.cvv.message}</small>
                                    )}
                                    onKeyUp={() => {
                                        trigger("cvv");
                                    }}
                                    value={cardnew?.cvv}
                                    onChange={handleInput}
                                    error={errors.cvv}

                                />
                                </Col> */}
                            </Row>
                            <Row>
                                <Col sm={6}>

                                </Col>
                                <Col sm={6} className="d-flex justify-content-end" >
                                    <Button type="submit" style={{ fontSize: '1.3rem' }} className="btn">{cardDetails ? "update" : "save"}</Button>
                                </Col>
                            </Row>

                        </Container>


                    </Form>
                </DialogContent>

            </Dialog>
        </div>

        </>
    );
};