import "../Style/Button.css"

function Button(props) {
    return (
        <button type="button" onClick={props.navigateTo} className="btn mb-3">{props.ButtonName}</button>
    )
};

export default Button;