import photo from './Logo.png'
import "../Style/Logo.css"


function Logo() {
    return (
        <div className='row logo-container'>
            <img src={photo} alt="logo" className='logo-img' />
        </div>
    )
};

export default Logo;    