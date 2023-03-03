import FeedbackCard from "./FeedbackCard";

function Feedback() {
    return (
        <div>
            <h2 className="text-center my-4">Let's hear from our Foodies</h2>
            <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <FeedbackCard feedback="Dinette gives us an immense choice for your hunger cravings. Now it really saves my time and I can keep my work as well as order food" />
                    </div>
                    <div class="carousel-item">
                        <FeedbackCard feedback="Most of the time I stay out of hometown and I do not have to worry about food because Dinette takes care of that. I can order food during night whenever I am working late and don't feel like cooking anything. I can order healthy food as well whenever I am on my diet." />
                    </div>
                    <div class="carousel-item">
                        <FeedbackCard feedback="Dinette has the best UI. The app is very easy to use. The support team of Zomato is amazing, I have lodged complaints many times regarding the wrong items sent by the restaurants and zomato has immediately initiated a refund." />
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon zbg-dark" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                    <span class="carousel-control-next-icon bg-dark" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
    )
};

export default Feedback;