import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useState } from 'react';
import {IoWarningOutline} from 'react-icons/io5'
// import Button from "./Button.jsx"

export function GeolocationAlert(props) {
  const [open, setOpen] = useState(true);


  const handleClose = () => {
    console.log('hggh')
    props.sendOk('ok')
    setOpen(false);
  };


  return (
    <div>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description" style={{color:"#FFE9A0"}}
      >
        <div  className='dialbox-brd'>
        <DialogTitle className='d-flex justify-content-center' style={{fontSize: "2rem"}}>
        <IoWarningOutline id="alert-dialog-title" />
        </DialogTitle>
        <DialogTitle id="alert-dialog-title" className='d-flex justify-content-center'>
          
          {"Google map location access denied !!"}
        </DialogTitle>
       
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Lets Allow Google map location will help you to search your area for our services.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button style={{ fontSize: '1rem' }} className="btn" onClick={handleClose}>OK</Button>
        </DialogActions>
        </div>
         
      </Dialog>
    </div>
  );
}
