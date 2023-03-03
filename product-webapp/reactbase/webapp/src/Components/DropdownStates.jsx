import { useEffect, useState } from "react"
import "../Style/Dropdown.css"
import { availableServices } from './CitiesService.js'


function DropdownStates(props) {
  const [trackedLocation, setTrackedLocation] = useState(availableServices);
  const [currentLocation, setCurrentLocation] = useState()





  useEffect(() => {
    const currentLocationvalue = props.currentLocationvalue;
    setCurrentLocation(currentLocationvalue)
    const filteredArr = availableServices.filter(el => {
      if (el.locationValue != currentLocationvalue) {
        return el
      }
    });
    setTrackedLocation(filteredArr);

    const cities = availableServices.map(res => res.locationValue);
    const isAvailable = cities.includes(props.currentLocationvalue)
    props.getIsAvailableservice(isAvailable)
  }, [props.currentLocationvalue, props.currentLocation])
  const selectOnchange = (event) => {
    setCurrentLocation(null);
    if (props.currentLocationvalue && event.target.value != currentLocation) props.selectDiffcity('showsetascurrent')
  }
  return (
    <>
      <select className="country-state" name="country-state" onChange={(e) => selectOnchange(e)} >
        <option value="">Select</option>
        {
          currentLocation ? <option value={currentLocation} selected="selected">{currentLocation}</option> : ""
        }
        {
          trackedLocation?.map(local => (
            <option value={local.locationValue}>{local.locations}</option>
          ))
        }
      </select>


    </>

  )
};

export default DropdownStates