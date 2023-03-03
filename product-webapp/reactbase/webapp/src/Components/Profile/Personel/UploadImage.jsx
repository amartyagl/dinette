
import PropTypes from 'prop-types';
import Button from '@mui/material/Button';
import { styled } from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import IconButton from '@mui/material/IconButton';
import { useEffect, useState } from 'react';
import { AiFillCamera } from 'react-icons/ai'
import Avatar from "react-avatar-edit"
import axios from 'axios';
import { Base_Url } from '../../BaseUrl';

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
    '& .MuiDialogContent-root': {
        padding: theme.spacing(2),
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
    },
}));

const BootstrapDialogTitle = (props) => {
    const { children, onClose, ...other } = props;

    return (
        <DialogTitle sx={{ m: 0, p: 2 }} {...other}>
            {children}
            {onClose ? (
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                    sx={{
                        position: 'absolute',
                        right: 8,
                        top: 8,
                        color: (theme) => theme.palette.grey[500],
                    }}
                >
                </IconButton>
            ) : null}
        </DialogTitle>
    );
};

BootstrapDialogTitle.propTypes = {
    children: PropTypes.node,
    onClose: PropTypes.func.isRequired,
};




export const UploadImage = (props) => {
    const [open, setOpen] = useState(false);
    const [info, setInfo] = useState([]);
    const [preview, setPreview] = useState(null);
    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };
    const onClose = () => {
        setPreview(null);
    }
    const onCrop = (view) => {
        setPreview(view);
    }

    useEffect(() => {
        if (props.email) {
            axios.get(`${Base_Url}/user-service/api/v1/getUser/${props.email}`, { headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization':  localStorage.getItem('token') 
              }}).then(res => {
                setInfo(res.data)
            }).catch(error=>{
                console.log(error)
            })
        }



    }, [props.model, props.email])

    const uploadImage = () => {
        console.log(info)
        const val = {
            email: info.email,
            name: info.name,
            mobileNumber: info.mobileNumber,
            profilePicture: preview
        }

        axios.put(`${Base_Url}/user-service/api/v1/updateUser`, val, { headers: {
            "Content-Type": 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization':  localStorage.getItem('token') 
          }}).then(res => {
            props.model("updated");
            handleClose();
        })
    }

    return (
        <div>
            <AiFillCamera onClick={handleClickOpen} />
            {/* <h1 className='text-center' style={{fontSize: '11px'}}>Change Profile Picture</h1> */}
            <BootstrapDialog
                onClose={handleClose}
                aria-labelledby="customized-dialog-title"
                open={open}
            >
                <BootstrapDialogTitle id="customized-dialog-title" className="d-flex justify-content-center" onClose={handleClose}>
                    Upload Image
                </BootstrapDialogTitle>
                <DialogContent dividers>
                    <Avatar
                        width={400}
                        height={300}
                        onCrop={onCrop}
                        onClose={onClose}
                    />
                    {preview && <img src={preview} alt="profile" />}
                </DialogContent>
                {preview &&
                    <DialogActions>
                        <Button autoFocus onClick={uploadImage} style={{ fontSize: '1.3rem' }} className="btn" disabled={!preview}>
                            Upload Image
                        </Button>
                    </DialogActions>
                }

            </BootstrapDialog>
        </div>
    );
}