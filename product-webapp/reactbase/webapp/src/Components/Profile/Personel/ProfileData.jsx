import axios from 'axios';
import { useEffect, useState } from 'react';
import { Card } from 'react-bootstrap';
import { AiOutlineMail } from 'react-icons/ai'
import { FiPhone } from 'react-icons/fi'
import { Base_Url } from '../../BaseUrl';
import { UploadImage } from './UploadImage';
export const ProfileData = (props) => {
    const [modeldata, setModeldata] = useState(null)
    const [info, setInfo] = useState({});
    useEffect(() => {
        if (props.email) {
            axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization':  localStorage.getItem('token') 
              }}).then(res => {
                setInfo(res.data);


            })
        }

    }, [props.childdata, modeldata, props.email]);

    const model = () => {

        setModeldata('true')
        axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(res => {
            props.profileupdate(res.data);
            setInfo(res.data);


        })

    }

    return (
        <>
            <div>
                <div className='d-flex justify-content-center'>
                    {
                        !info?.profilePicture ? <div className="container-data pt-3">
                             <i className=" bi bi-person-circle" type="button" data-bs-toggle="dropdown" aria-expanded="false" style={{fontSize: "8rem"}}>
                        </i>
                            {/* <img src="data:image/jpeg;base64,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" alt="Snow" className='image-class img-default' /> */}

                            <div className="centered"> <UploadImage model={model} email={props.email} /></div>
                        </div> : info?.profilePicture &&
                        <>
                            <div className="container-data pt-3">
                                <img src={info.profilePicture} alt="Snow" className='image-class' />

                                <div className="centered"> <UploadImage model={model} email={props.email} /></div>
                            </div>
                        </>
                    }
                </div>
            </div>
            <div style={{ paddingTop: "2rem", color: "#000" }}>
                <h3>{`${info.name}`}</h3>
                <h6> <AiOutlineMail style={{ fontSize: "2rem", color: "#850E35", marginRight: "8px" }} />  {info.email}</h6>
                <h6> <FiPhone style={{ fontSize: "2rem", color: "#850E35" }} /> +91 {info.mobileNumber}</h6>
            </div>


            {/* <Card.Body >
                    <Card.Title className='d-flex justify-content-center' style={{ fontSize: '1.7rem' }}  >{`${info.name}`}</Card.Title>
                    <Card.Text className='d-flex justify-content-center' style={{ fontSize: '1.3rem' }}>
                        <AiOutlineMail style={{ fontSize: "2rem", color: "#850E35", marginRight: "8px" }} />  {info.email}
                    </Card.Text>
                    <Card.Text className='d-flex justify-content-center' style={{ fontSize: '1.3rem' }}>
                        <FiPhone style={{ fontSize: "2rem", color: "#850E35" }} /> +91 {info.mobileNumber}
                    </Card.Text>
                </Card.Body> */}
        </>
    )
}