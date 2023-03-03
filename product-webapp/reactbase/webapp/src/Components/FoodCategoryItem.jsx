import "../Style/FoodCategoryItem.css"

function FoodCategoryItem(props) {
    return (
        <div>
            <div className="container-FoodCategoryItem" >
                <img className="image-FoodCategoryItem" src={props.src} alt="foodCat" />
            </div>
            <div>
                <h5 className="font-weight-bolder text-center">{props.items}</h5>
            </div>
        </div>

    )
};

export default FoodCategoryItem;