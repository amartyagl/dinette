import axios from "axios"
import { useEffect, useState } from "react"
import { Card, Col, Container, Row } from "react-bootstrap"
import { MdDelete } from "react-icons/md";
import { HiOutlinePencil } from 'react-icons/hi';
import { ButtonBase, Typography, Paper, Grid, styled, Box } from '@mui/material';
import { Base_Url } from "../../BaseUrl";



const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));
export const Atmcards = (props) => {
    const [cards, setCards] = useState('');
    const [addcard, setAddcard] = useState('');
    const [deleted, setDelete] = useState();



    useEffect(() => {
        if (props.email) {
            axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization':  localStorage.getItem('token') 
              }}).then(res => {
                setCards(res.data.cardDetails);
            })
        }

    }, [addcard, props.carddata, deleted, props.email]);


    const deleteCard = (id) => {
        axios.delete(`${Base_Url}/user-service/api/v1/deleteCardDetails/${props.email}/${id}`, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(data => {
            if (data.data) {
                axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
                    "Content-Type": 'application/json',
                    'Access-Control-Allow-Origin': '*',
                    'Authorization':  localStorage.getItem('token') 
                  }}).then(res => {
                    setCards(res.data.cardDetails);
                })
            }
            setDelete(data.data);
            // setTimeout(() => {
            setAddcard(props.carddata)
            // })

        })
    }
    const editcard = (id) => {
        axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(response => {
            const carddata = response.data.cardDetails.find(x => x.cardNumber === id);
            const cardDetails = {
                cardNumber: carddata.cardNumber,
                name: carddata.name,
                date: [carddata.expiryMonth, carddata.expiryYear].join('/')
            }
            props.editEvent(cardDetails)

        })
    }
    return (
        <>
            {
                cards?.length > 0 ? cards.map((data, i) => (
                    <Paper
                        sx={{
                            p: 6,
                            margin: 'auto',
                            maxWidth: 700,
                            marginBottom: "1.5rem",
                            flexGrow: 1,
                            backgroundColor: "#FFC4C4"
                        }}
                    >
                        <Grid container spacing={2}>

                            <Grid item sm container>
                                <Grid item xs container direction="column" spacing={2}>
                                    <Box sx={{ flexGrow: 1, fontSize: "1.1rem" }}>
                                        <Grid container >

                                            <Grid item xs={6} md={4} style={{fontWeight: "700"}}>
                                                Card Name:
                                            </Grid>
                                            <Grid item xs={6} md={8}>
                                                {data.name}
                                            </Grid>
                                        </Grid>
                                    </Box>
                                    <Box sx={{ flexGrow: 1, fontSize: "1.1rem" }}>
                                        <Grid container >

                                            <Grid item xs={6} md={4} style={{fontWeight: "700"}}>
                                                Card Number:
                                            </Grid>
                                            <Grid item xs={6} md={8}>
                                                {data.cardNumber}
                                            </Grid>
                                        </Grid>
                                    </Box>
                                    <Box sx={{ flexGrow: 1, fontSize: "1.1rem" }}>
                                        <Grid container >

                                            <Grid item xs={6} md={4} style={{fontWeight: "700"}}>
                                                Expiry Date:
                                            </Grid>
                                            <Grid item xs={6} md={8}>
                                                {data.expiryMonth}/{data.expiryYear}
                                            </Grid>
                                        </Grid>
                                    </Box>
                                </Grid>
                                <Grid item>
                                    <Typography variant="subtitle1" component="div" style={{ padding: "0rem 2rem 2rem 2rem" }}>
                                        < HiOutlinePencil title="Edit" style={{ fontSize: "1.8rem" }} onClick={() => editcard(data.cardNumber)} />
                                    </Typography>
                                </Grid>
                                <Grid item>
                                    <Typography variant="subtitle1" component="div">
                                        < MdDelete title="Delete" style={{ fontSize: "1.8rem" }} onClick={() => deleteCard(data.cardNumber)} />
                                    </Typography>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Paper>

                )) : "No Record Found"}
        </>

    )
}