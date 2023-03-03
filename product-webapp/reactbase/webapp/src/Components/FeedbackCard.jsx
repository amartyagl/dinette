import "../Style/FeedbackCard.css"

function FeedbackCard(props) {
    return (
        <div className="FeedbackCard-container mb-4">
            <div>
                <h2><i className="bi bi-quote mx-5"></i></h2>
                <div className="Feedback-text text-center">{props.feedback} </div>
                <h2 className="quote-end"><i className="bi bi-quote"></i></h2>
            </div>
        </div>
    )
};

export default FeedbackCard;