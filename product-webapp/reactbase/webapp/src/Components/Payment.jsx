import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Base_Url } from './BaseUrl'

function Payment(props) {

    if (!props.open) {
        return null;
    }
    const url = `${Base_Url}/payment-service/v1/api/payment`;

    const paymentStatus = () => {
        props.successfulpayment()
    }
    let amt = props.subscribeInfo.prize;
    const userDatail = localStorage.getItem('userDetails')
    const email = JSON.parse(userDatail).email;
    props.close(false)


    const initiatePayment = () => {
        if (amt > 0) {
            const paymentData = {
                emailId: email,
                amount: amt
            }
            // create order on server
            fetch(url, {
                method: 'POST',
                headers: {
                    "Content-Type": 'application/json',
                    'Authorization': localStorage.getItem('token')
                },
                body: JSON.stringify(paymentData)
            }).then(res => res.json())
                .then(data => {
                    // generate payment for orderId
                    var options = {
                        "key": "rzp_test_06VdS7gnGPbP3i",
                        "amount": amt,
                        "currency": "INR",
                        "name": "Dinette",
                        "description": "Test Transaction",
                        "image": "https://example.com/your_logo",
                        "order_id": data.id,
                        "handler": function (response) {
                            var values = {
                                // myPaymentId: 1,
                                paymentId: response.razorpay_payment_id,
                                orderId: response.razorpay_order_id,
                                emailId: email,
                                amount: amt,
                                status: data.status,
                                receipt: data.receipt
                            }
                            // update order
                            fetch(url, {
                                method: 'PUT', headers: {
                                    "Content-Type": 'application/json',
                                    'Authorization': localStorage.getItem('token')
                                },
                                body: JSON.stringify(values)
                            }).then(res => res.json())
                                .then(data1 => {
                                    toast.success(`payment successful !!!`)
                                    paymentStatus();
                                })
                        },
                        "prefill": {
                            "name": "Gaurav Kumar",
                            "email": "gaurav.kumar@example.com",
                            "contact": "9999999999"
                        },
                        "notes": {
                            "address": "Razorpay Corporate Office"
                        },
                        "theme": {
                            "color": "#3399cc"
                        }

                    };
                    const rzp1 = new window.Razorpay(options)
                    rzp1.on('payment.failed', function (response) {
                        toast.error(`payment failed !!!`)
                    });
                    rzp1.open();
                })


        }
    }

    return (
        <div>
            {
                initiatePayment()
            }

        </div>
    )
}

export default Payment;