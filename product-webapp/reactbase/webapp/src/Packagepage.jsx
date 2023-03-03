import Button from "./Components/Button";
import "./Style/Subscription.css"
import React from "react";
import Payment from "./Components/Payment";
import CornerRibbon from "react-corner-ribbon";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Base_Url } from "./Components/BaseUrl";
function Packagepage() {
    const [SubscribeDetail, setSubscribeDetails] = React.useState([])
    const [openPayment, setOpenPayment] = React.useState(false)
    const [subscribe, SetSubscribe] = React.useState([])
    React.useEffect(() => {
        setSubscribeDetails([
            {
                "id": 101,
                "subscriptionName": "SILVER",
                "package": "SILVER",
                "benefits": "Benefits",
                "subscriptinId": 101,
                "subscriptionImage": "https://i.postimg.cc/y8dmkQ0D/Silver-Package-Graphics-Design.png",
                "prize": 300,
                "ul": {
                    "li1": "Order upto 3 items in a meal",
                    "li2": "One free delivery per week",
                    "li3": "Limited items to choose from",
                    "li4": "Limited offers and deals"
                },
                "cssId": "card-container--silver",
                "cardCssId": "top--silver"

            },
            {
                "id": 102,
                "subscriptionName": "GOLD",
                "package": "GOLD",
                "benefits": "Benefits",
                "subscriptinId": 102,
                "subscriptionImage": "https://i.postimg.cc/25bgF5Ff/gold-package-e1517317572725.png",
                "prize": 500,
                "ul": {
                    "li1": "Order upto 4 items in a meal",
                    "li2": "Three free delivery per week",
                    "li3": "Limited items to choose fromAccess to premium dishes",
                    "li4": "Get weekly offers"
                },
                "cssId": "card-container--gold",
                "cardCssId": "top--gold"
            },
            {
                "id": 103,
                "subscriptionName": "PLATINUM",
                "package": "PLATINUM",
                "benefits": "Benefits",
                "prize": 700,
                "subscriptinId": 103,
                "subscriptionImage": "https://i.postimg.cc/Pq1PxYVL/Platinum-Package-Graphics-Design.png",
                "ul": {
                    "li1": "No limit on items",
                    "li2": "Free delivery",
                    "li3": "Access to premium dishes",
                    "li4": "Get multiple offers in a week"
                },
                "cssId": "card-container--platinum",
                "cardCssId": "top--platinum"
            }
        ])
    }, [])

    const navigateToPackages = (data) => {
        SetSubscribe(data)
        setOpenPayment(true)
        console.log(data, 'data ===>')

    }


    const PaymentStatus = () => {
        const l1 = subscribe?.ul?.li1
        const l2 = subscribe?.ul?.li2
        const l3 = subscribe?.ul?.li3
        const l4 = subscribe?.ul?.li4
        let subscriptionDescription = l1 + ', ' + l2 + ', ' + l3 + ', ' + l4
        const subscriptionId = subscribe.id
        // const emailId = localStorage.getItem('user')
        const userDatail = localStorage.getItem('userDetails')
        const emailId = JSON.parse(userDatail).email
        const subscriptionName = subscribe.subscriptionName
        const subscriptionPrize = subscribe.prize
        const purchaseDate = new Date().toLocaleDateString()
        const getdate = new Date().getDate()
        console.log(getdate)
        const setdate = getdate + 30
        console.log(setdate)
        const endDate = new Date().setDate(setdate)

        const url = `${Base_Url}/subscription-service/v1/api/addSubscription/`

        // const url = 'http://localhost:8080/subscription-service/v1/api/addSubscription/'
        // const url = 'http://localhost:1234/subscriptionUserList/';
        const data = {
            subscriptionId: subscriptionId,
            emailId: emailId,
            firstName: 'Anoop',
            subscriptionName: subscriptionName,
            subscriptionPrize: subscriptionPrize,
            purchaseDate: purchaseDate,
            subscriptionImage: '',
            endDate: new Date(endDate).toLocaleDateString(),
            subscriptionDescription: subscriptionDescription

        }
        fetch(url, {
            method: 'POST',
            headers: {
                "Content-Type": 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('token')
            },
            body: JSON.stringify(data)
        }).then(response => {
            if (response.status === 201) {
                toast.success(` ${subscriptionName} subscription added in account your ${emailId}`)
            } else {
                toast.error('please check may be you have already purchase a subscription')
            }

        }).catch((e) => {
            console.log(e.errorMessage)
            toast.error('api failing please try again')
            // alert('api failding')
        })

    };


    return (

        <div>
            <h2 className="text-center">Choose a Package</h2>
            <div className="row justify-content-center" >
                {
                    SubscribeDetail.map((subs, index) => {
                        return (

                            <div className="m-4 " style={{ width: "20rem", position: "relative" }}>
                                <CornerRibbon
                                    position="top-left"
                                    fontColor="#f0f0f0"
                                    backgroundColor="#B66A7B"
                                >
                                    {subs.package}
                                </CornerRibbon>
                                <div id={subs.cssId == "card-container--platinum" ? "w-100 h-10 bg-warning" : "d-none"}>

                                </div>
                                <div className="top text-center" id={subs.cardCssId}>
                                    <img src={subs.subscriptionImage} width="80%" /></div>
                                <div className="card-body card-container p-3 pb-0" id={subs.cssId}>
                                    <h5 className="card-title">{subs.package}</h5><br />
                                    <ul key={index}>
                                        <li>{subs.ul.li1}</li>
                                        <li>{subs.ul.li2}</li>
                                        <li>{subs.ul.li2}</li>
                                        <li>{subs.ul.li4}</li>

                                    </ul>
                                    <div className="d-flex">
                                        <div className="text-left" style={{ minWidth: '60%' }}>
                                            <p>₹ {subs.prize} / M</p>
                                        </div>
                                        <div className="text-end">
                                            <Button navigateTo={() => { navigateToPackages(subs) }} ButtonName="Subscribe" />
                                        </div>
                                    </div>

                                </div>


                            </div>
                        )
                    })
                }


                {
                    openPayment &&
                    <Payment open={openPayment} successfulpayment={PaymentStatus} subscribeInfo={subscribe} close={() => { setOpenPayment(false) }} />
                }
            </div>
        </div>


    )
};

export default Packagepage;