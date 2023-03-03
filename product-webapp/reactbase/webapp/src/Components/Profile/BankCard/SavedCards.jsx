import { useEffect, useState } from "react"
import { Atmcards } from "./Atmcards"
import { ControlledPopup } from "./ControlledPopup"

export const SavedCards = (props) => {
    const [carddata, setCarddata] = useState();
    const [edit, setEdit] = useState();
    useEffect(() => {

        handleVal();
    }, [carddata, edit])
    const handleVal = (event) => {
        setCarddata(event)
    }

    const handleEdit = (event)=>{
        setEdit(event)
    }
    return (
        <>
            <Atmcards carddata={carddata} editEvent={handleEdit} email={props.email}/>
            <ControlledPopup handlepopup={handleVal} userId={props.userId} editPopup={edit} email={props.email}/>
        </>

    )
}