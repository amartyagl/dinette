import { useEffect, useState } from 'react';
import { Accordion, Card, Col, Row } from 'react-bootstrap';
import { ProfileData } from './Personel/ProfileData';
import { Order } from './Orders/Order';
import { SavedAddress } from './Address/SavedAddress';
import axios from 'axios';
import { Payment } from './Payment/Payment';
import { ProfileInfo } from './Personel/ProfileInfo';
import { SavedCards } from './BankCard/SavedCards';
import "../../Style/Profile.css"
import { Box, Grid, Paper, styled } from '@mui/material';
import {Base_Url} from '../BaseUrl.jsx';


const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#FFF5E4',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));


export const Profile = () => {
    const [childdata, setChilddata] = useState('');
    const [profiledata, setProfiledata] = useState();
    const [profilechange, setProfileChange] = useState();
    const [email, setEmaildata] = useState(null)

    useEffect(() => {
        handlesaved();
        const userDetails = localStorage.getItem('userDetails') ? localStorage.getItem('userDetails') : ''
        const emailval = userDetails ? JSON.parse(userDetails).email : '';
        setEmaildata(emailval)
        axios.get(`${Base_Url}/user-service/api/v1/getUser/${emailval}`, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(res => {
            setProfiledata(res.data);
        })
    }, [childdata])
    const handlesaved = (data) => {
        setChilddata(data)
    }

    const profileupdate = (dataupdated) => {
        setProfileChange(dataupdated)
    }
    return (

        <Box sx={{ flexGrow: 1, padding: "1rem 2rem 1rem 2rem" }}>
            <Grid container spacing={3}>
                <Grid item xs={12} md={3} sm={12}>
                    <Item className="first-col" style={{ border: "2px solid #850E35" }}><ProfileData childdata={childdata} profileupdate={profileupdate} email={email} /></Item>
                </Grid>
                <Grid item xs={12} md={9} sm={12}>
                    <Item className="first-col" style={{ border: "2px solid #850E35" }}>
                        <Accordion>
                            <Accordion.Item eventKey="0">
                                <Accordion.Header>Personnel Information</Accordion.Header>
                                <Accordion.Body style={{ padding: "2em 8em" }}>
                                    <ProfileInfo savedDetails={handlesaved} profilechange={profilechange} email={email} />
                                </Accordion.Body>
                            </Accordion.Item>
                            <Accordion.Item eventKey="1">
                                <Accordion.Header>Manage Address</Accordion.Header>
                                <Accordion.Body>
                                    <SavedAddress userId={profiledata?.id} email={email} />
                                </Accordion.Body>
                            </Accordion.Item>

                            <Accordion.Item eventKey="2">
                                <Accordion.Header>Manage Card</Accordion.Header>
                                <Accordion.Body>
                                    <SavedCards userId={profiledata?.id} email={email} />
                                </Accordion.Body>
                            </Accordion.Item>
                            <Accordion.Item eventKey="3">
                                <Accordion.Header>Order History</Accordion.Header>
                                <Accordion.Body>
                                    <Order email={email} />
                                </Accordion.Body>
                            </Accordion.Item>
                            <Accordion.Item eventKey="4">
                                <Accordion.Header>Payment History</Accordion.Header>
                                <Accordion.Body>
                                    <Payment email={email} />
                                </Accordion.Body>
                            </Accordion.Item>

                        </Accordion>
                    </Item>
                </Grid>
            </Grid>
        </Box>

        // <Row className="pl-3 row-data-val">

        //     <Col sm={3}  className="col-effect">
        //         <ProfileData childdata={childdata} profileupdate={profileupdate} email={email}/>
        //     </Col>
        //     <Col sm={8}>
        //         <Card className="first-col">
        //             <Card.Body>
        // <Accordion>
        //     <Accordion.Item eventKey="0">
        //         <Accordion.Header>Personnel Information</Accordion.Header>
        //         <Accordion.Body style={{ padding: "2em 8em" }}>
        //             <ProfileInfo savedDetails={handlesaved} profilechange={profilechange} email={email}/>
        //         </Accordion.Body>
        //     </Accordion.Item>
        //     <Accordion.Item eventKey="1">
        //         <Accordion.Header>Manage Address</Accordion.Header>
        //         <Accordion.Body>
        //             <SavedAddress userId={profiledata?.id} email={email}/>
        //         </Accordion.Body>
        //     </Accordion.Item>

        //     <Accordion.Item eventKey="2">
        //         <Accordion.Header>Your Cards</Accordion.Header>
        //         <Accordion.Body>
        //             <SavedCards userId={profiledata?.id} email={email}/>
        //         </Accordion.Body>
        //     </Accordion.Item>
        //     <Accordion.Item eventKey="3">
        //         <Accordion.Header>Your Orders</Accordion.Header>
        //         <Accordion.Body>
        //             <Order email={email}/>
        //         </Accordion.Body>
        //     </Accordion.Item>
        //     <Accordion.Item eventKey="4">
        //         <Accordion.Header>Your Payments</Accordion.Header>
        //         <Accordion.Body>
        //             <Payment email={email}/>
        //         </Accordion.Body>
        //     </Accordion.Item>

        // </Accordion>
        //             </Card.Body>
        //         </Card>
        //     </Col>
        // </Row>


    )
}