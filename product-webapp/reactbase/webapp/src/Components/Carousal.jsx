import photo from './dummy-image.jpg'
import photo2 from './dummy-img-2.jpg'
import photo3 from './dummy-img-3.jpg'
import '../Style/Carousal.css'
import DropdownStates from './DropdownStates'
import Button from "./Button.jsx"
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { useEffect } from 'react'
import { GeolocationAlert } from './GeolocationAlert'
import axios from 'axios';
import { BiCurrentLocation } from 'react-icons/bi';
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';
import { Grid, Paper, Box, styled } from '@mui/material';
import { IconButton, Tooltip } from '@mui/material'

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

function Carousal() {
    const [locationerror, setError] = useState(null);
    const [locationDest, setLocationDest] = useState({});
    const navigate = useNavigate();
    const navigateToMenu = () => navigate('/menu');
    const [currentLocation, setCurrentLocation] = useState(null);

    const [showselectascurrent, setShowselectascurrent] = useState();
    const [availibityofFoodService, setAvailabilityofService] = useState(true);

    // =============================================
    const [currentLocationvalue, setCurrentLocationValue] = useState(null);
    useEffect(() => {
        setCurrentLocationValue(null);
        setAvailabilityofService(true)
    }, [])

    const tracklocation = () => {
        const digit = Math.random(1, 100)
        const currentlocation = '';
        setCurrentLocation(digit, currentlocation);
        setShowselectascurrent(null);
        detectCurrentLocation()

    }

    const detectCurrentLocation = () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(getCoordinates, handleLocationError);
        } else {
            alert('geo Location is Supported By this Browser');
        }
    }
    function getCoordinates(position) {
        setLocationDest(position.coords);
        reverseGeoCodeCoordinates(position.coords);
    }

    function reverseGeoCodeCoordinates(locationDest) {
        const { latitude, longitude } = locationDest;

        currentLocationvalue ? tracklocation() :
            axios.get(`https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${latitude}&longitude=${longitude}&localityLanguage=en`).then(data => {
                setCurrentLocationValue(`${data.data?.city}`)
            })
    }
    function handleLocationError(error) {
        setAvailabilityofService(true)
        setCurrentLocationValue(null)
        switch (error.code) {
            case error.PERMISSION_DENIED:
                setError(error);
                break;
            case error.POSITION_UNAVAILABLE:
                setError(error);
                break;
            case error.TIMEOUT:
                setError(error);
                break;
            case error.UNKNOWN_ERROR:
                setError(error);
                break;
            default: alert('not matching')
        }

    }
    const sendOk = (ok) => {
        setError(null);
        setAvailabilityofService(true)
    }

    const selectDiffcity = (showselectascurrent) => {
        setShowselectascurrent(showselectascurrent);
        setAvailabilityofService(true)
    }

    const getIsAvailableservice = (isavailable) => {
        setAvailabilityofService(isavailable)
    }
    return (
        <div className='wrapper'>
            <div id="carouselExampleInterval" class="carousel slide mt-2" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active" data-bs-interval="2000">
                        
<img src={photo} class="d-block w-100" alt="..." />
                    </div>
                    <div class="carousel-item active" data-bs-interval="2000">
                        <img src={photo2} class="d-block w-100" alt="..." />
                    </div>
                    <div class="carousel-item">
                        <img src={photo3} class="d-block w-100" alt="..." />
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>

            <div className='search-container'>



                {locationerror ? <GeolocationAlert sendOk={sendOk} /> : ""}

                <h1 className='text-center'>
                    Discover the best food & drinks
                </h1>
                <h1 className='text-center'>
                    select your city
                </h1>
                <div className='search d-block text-center'>

                    <Box sx={{ flexGrow: 2 }}>
                        <Grid container spacing={1} columns={16}>
                            <Grid item xs={2} style={{ padding: "1.3rem 0rem 0rem 1rem" }}>
                                {
                                    !currentLocationvalue ?
                                        <Tooltip title="Detect your current location" arrow style={{ border: "3px solid rgba(133, 14, 53, 0.6)" }}>
                                            <IconButton onClick={detectCurrentLocation}>
                                                <BiCurrentLocation style={{ fontSize: "1.4rem", color: "rgba(133, 14, 53, 0.6)" }} />
                                            </IconButton>
                                        </Tooltip> : <Tooltip title="Detect your current location" arrow style={{ border: "3px solid rgba(133, 14, 53, 0.6)" }}>
                                            <IconButton onClick={tracklocation}>
                                                <BiCurrentLocation style={{ fontSize: "1.4rem", color: "rgba(133, 14, 53, 0.6)" }} />
                                            </IconButton>
                                        </Tooltip>

                                }
                            </Grid>
                            <Grid item xs={12}>
                                <DropdownStates currentLocationvalue={currentLocationvalue} selectDiffcity={selectDiffcity} currentLocation={currentLocation} getIsAvailableservice={getIsAvailableservice} />
                            </Grid>
                            <Grid item xs={2} style={{ padding: "1.5rem 0rem 0rem 0rem" }}>
                                <Button navigateTo={navigateToMenu} ButtonName="Go" />
                            </Grid>
                        </Grid>
                    </Box>












                </div>
                {
                    availibityofFoodService ? " " : <Stack sx={{ width: '100%', padding: "0rem 2rem 2rem 2rem", bottom: "0" }} spacing={2}>
                        <Alert variant="filled" severity="error">
                            Currently our services are not available in your city please select another city!
                        </Alert>
                    </Stack>
                }
            </div>
        </div>

    )
};

export default Carousal