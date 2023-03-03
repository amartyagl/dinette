import { useEffect, useState } from "react"
import { Address } from "./Address";
import { AddressPopup } from "./AddressPopup";

export const SavedAddress = (props) => {
    const [address, setAddress] = useState();
const [handleedit, setHandledit] = useState();
    const handlepopup = (save) => {
        setAddress(save)
    }
    const handleEditAddress = (editAddress)=>{
        setHandledit(editAddress);
    }
    useEffect(()=>{
        handlepopup()
    },[address, handleedit])
    return (
        <>
            <Address addedAddress={address} handleEditAddress={handleEditAddress} email={props.email}/>
            <AddressPopup handlepopup={handlepopup} userId={props.userId} handleedit={handleedit} email={props.email}/>
        </>
    )
}