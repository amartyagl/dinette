import { MdChevronLeft, MdChevronRight } from 'react-icons/md';
function Explore(foodItems) {
    const slideLeft = () => {
        var slider = document.getElementById('slider');
        slider.scrollLeft = slider.scrollLeft - 500;
      };
    
      const slideRight = () => {
        var slider = document.getElementById('slider');
        slider.scrollLeft = slider.scrollLeft + 500;
      };

    return (
            <div className="relative flex items-center explore-container d-flex">
                <MdChevronLeft className='opacity-50 cursor-pointer hover:opacity-100' onClick={slideLeft} size={40} />
                <div id='slider' className="w-full h-full overflow-x-scroll scroll whitespace-nowrap scroll-smooth scrollbar-hide">
                    {foodItems.foodItems.map((ele, index) => {
                return (
                    <div key={index} className="w-[220px]  inline-block p-2 cursor-pointer hover:scale-105 ease-in-out duration-300">
                        <button ><img src={ele.foodPicture} className="exploreImg" /></button>
                        <p style={{ textAlign: "center", fontSize:"20px", marginTop:"10px" }}>{ele.foodCategory[0]}</p>
                    </div>
                )
            })
            }
                </div>
                <MdChevronRight className='opacity-50 cursor-pointer hover:opacity-100' onClick={slideRight} size={40} />
                
            </div>
            
    )
}
export default Explore;