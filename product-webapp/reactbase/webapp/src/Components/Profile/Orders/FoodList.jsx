import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { useState } from 'react';
import { BsEye } from 'react-icons/bs'
import { AiOutlineClose } from 'react-icons/ai'
import { styled } from '@mui/material/styles';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import ButtonBase from '@mui/material/ButtonBase';


const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
});

export const Foodlist = (props) => {
    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };


    return (
        <div>

            <BsEye onClick={handleClickOpen} style={{ fontSize: "1.5rem" }} title="View Food List" className='view-icon' />
            <Dialog open={open} onClose={handleClose} fullWidth="md" maxWidth="md">
                <div className='close-button'>
                    <AiOutlineClose onClick={handleClose} />
                </div>

                <DialogTitle className='title-page'>Order Items</DialogTitle>
                <hr />
                <DialogContent>
                    {
                        props.foodList?.map(food => (
                            <Paper
                                sx={{
                                    p: 2,
                                    margin: 'auto',
                                    width: "100%",
                                    flexGrow: 1,
                                    marginBottom: "2rem",
                                    backgroundColor: (theme) =>
                                        theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
                                }}
                                className="table-class"
                            >
                                <Grid container spacing={2}>
                                    <Grid item>
                                        <ButtonBase style={{ width: "190px", height: "130px" }}>
                                            <Img alt="complex" style={{
                                                border: "1px solid #850E35",
                                                borderRadius: "0.5rem"
                                            }} src={food?.foodPicture} />
                                        </ButtonBase>
                                    </Grid>
                                    <Grid item xs={12} sm container>
                                        <Grid item xs container direction="column" spacing={2}>
                                            <Grid item xs>
                                                <Typography gutterBottom variant="subtitle1" component="div" sx={{
                                                    fontSize: "1.5rem", fontWeight: 600
                                                }}>
                                                    {food?.foodName}
                                                </Typography>
                                                <Typography variant="body2" gutterBottom>
                                                    {food?.foodDescription}
                                                </Typography>
                                                {/* <Typography variant="body2" color="text.secondary">
                                                    {food?.foodSize}
                                                </Typography> */}
                                            </Grid>

                                        </Grid>
                                        {
                                            food?.foodQty ?
                                                <Grid item xs container direction="column">
                                                    <Typography variant="subtitle1" component="div" >
                                                        <span>Qty</span>: <span className='food-qty'>{food?.foodQty}</span>
                                                    </Typography>
                                                </Grid> :
                                                ""

                                        }
                                        {
                                            food?.foodPrice ? <Grid item>
                                                <Typography variant="subtitle1" component="div">
                                                    <span>Price: </span> <span className='food-qty'>${food?.foodPrice}</span>
                                                </Typography>
                                            </Grid> : ""
                                        }

                                    </Grid>
                                </Grid>

                            </Paper>
                        ))
                    }
                </DialogContent>
            </Dialog>
        </div>
    );
}