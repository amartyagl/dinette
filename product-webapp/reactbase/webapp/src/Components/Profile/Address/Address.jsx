import axios from "axios";
import { useEffect, useState } from "react"
import { Card, Col, Container, Row } from "react-bootstrap"
import { MdDelete } from "react-icons/md"
import { HiOutlinePencil } from 'react-icons/hi'

import { ButtonBase, Typography, Paper, Grid, styled, Box } from '@mui/material';
import { Base_Url } from "../../BaseUrl";



const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));
export const Address = (props) => {
    const [addresList, setAddressList] = useState();
    const [event, setEvent] = useState();

    useEffect(() => {
        if (props.email) {
            axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization':  localStorage.getItem('token') 
              }}).then(response => {
                setAddressList(response.data.address)
            })
        }

    }, [props.addedAddress, event, props.email])


    const deleteAddress = (id) => {
        axios.delete(`${Base_Url}/user-service/api/v1/deleteAddress/${props.email}/${id}`, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(response => {
            if (response.data) {
                axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
                    "Content-Type": 'application/json',
                    'Access-Control-Allow-Origin': '*',
                    'Authorization':  localStorage.getItem('token') 
                  }}).then(response => {
                    setAddressList(response.data.address)
                })
            }
            setEvent(response.data)
        })
    }
    const editAddress = (id) => {
        axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(res => {
            const addresById = res.data.address.find(x => x.addressId === id);
            props.handleEditAddress(addresById);
        })

    }

    return (
        <>


            <Box sx={{ flexGrow: 1, padding: "2rem 5rem 2rem 5rem"}}>
                <Grid container spacing={1} columns={8}>
                    {addresList?.length > 1 ? addresList.map((address, index) => (
                        <Grid item xs={4} md={4} sm={8}>
                            <Paper
                                sx={{
                                    p: 3,
                                    margin: 'auto',
                                    maxWidth: 400,
                                    marginBottom: "1.5rem",
                                    flexGrow: 1,
                                    backgroundColor: "#FFC4C4",
                                }}
                            >
                                <Grid container spacing={2}>
                                    <Grid item  sm container >
                                        <Grid item xs container direction="column" spacing={2} >
                                            <Grid item xs >
                                                <Typography gutterBottom variant="body1" style={{fontSize: "1.4rem"}}>
                                                {address.houseNumber}
                                                </Typography>
                                                <Typography variant="body1" gutterBottom style={{fontSize: "1.4rem"}}>
                                                {address.colony}
                                                </Typography>
                                                <Typography variant="body1" style={{fontSize: "1.4rem"}}>
                                                {address.city}
                                                </Typography>
                                                <Typography variant="body1" style={{fontSize: "1.4rem"}}>
                                                {address.state}
                                                </Typography>
                                                <Typography variant="body1" style={{fontSize: "1.4rem"}}>
                                                {address.pincode}
                                                </Typography>
                                            </Grid>
                                        </Grid>
                                        <Grid item>
                                            <Typography variant="body1" style={{fontSize: "1.8rem", padding: "0rem 2rem 2rem 2rem"}}>
                                            < HiOutlinePencil title="Edit" onClick={() => editAddress(address.addressId)} />
                                            </Typography>
                                        </Grid>
                                        <Grid item>
                                            <Typography variant="body1" style={{fontSize: "1.8rem"}}>
                                            < MdDelete title="Delete" onClick={() => deleteAddress(address.addressId)} />
                                            </Typography>
                                        </Grid>
                                    </Grid>

                                </Grid>
                            </Paper>
                        </Grid>
                    )) : addresList?.length === 1 ? addresList.map((address, index) => (
                        <Grid item xs={8} md={8} sm={8}>
                            <Paper
                                sx={{
                                    p: 3,
                                    margin: 'auto',
                                    maxWidth: 400,
                                    flexGrow: 1,
                                    backgroundColor: "#FFC4C4",
                                }}
                            >
                                <Grid container spacing={2}>
                                    <Grid item  sm container >
                                        <Grid item xs container direction="column" spacing={2} >
                                            <Grid item xs >
                                                <Typography gutterBottom variant="body1" style={{fontSize: "1.4rem"}}>
                                                {address.houseNumber}
                                                </Typography>
                                                <Typography variant="body1" gutterBottom style={{fontSize: "1.4rem"}}>
                                                {address.colony}
                                                </Typography>
                                                <Typography variant="body1" style={{fontSize: "1.4rem"}}>
                                                {address.city}
                                                </Typography>
                                                <Typography variant="body1" style={{fontSize: "1.4rem"}}>
                                                {address.state}
                                                </Typography>
                                                <Typography variant="body1" style={{fontSize: "1.4rem"}}>
                                                {address.pincode}
                                                </Typography>
                                            </Grid>
                                        </Grid>
                                        <Grid item>
                                            <Typography variant="body1" style={{fontSize: "1.8rem", padding: "0rem 2rem 2rem 2rem"}}>
                                            < HiOutlinePencil title="Edit" onClick={() => editAddress(address.addressId)} />
                                            </Typography>
                                        </Grid>
                                        <Grid item>
                                            <Typography variant="body1" style={{fontSize: "1.8rem"}}>
                                            < MdDelete title="Delete" onClick={() => deleteAddress(address.addressId)} />
                                            </Typography>
                                        </Grid>
                                    </Grid>

                                </Grid>
                            </Paper>
                        </Grid>)) : "No Record Found"},
                </Grid>
            </Box>
        </>
    )
}