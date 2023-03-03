import { useState } from "react";
import BestSeller from "./BestSeller";
import React, { useEffect } from 'react'
import ListGroup from 'react-bootstrap/ListGroup';
import MenuSection from "./MenuSection";
import SearchIcon from '@mui/icons-material/Search';

function Search({ foodItems }) {
    const [query, setQuery] = useState("");
    let searchResult;
    function qSearch(data) {
        searchResult = data.filter((item) => item.foodName.toLowerCase().includes(query))
    }
    qSearch(foodItems);
    // console.log(searchResult)
    // props.getDatas(searchResult);
    //
    //


     const category = ['Rice and Biryani', 'Paneer', 'Chicken', 'Paratha and Breads', 'Pizza', 'Pasta', 'Soup', 'Chinese', 'Dal', 'Dessert'];

  const [filterData, setFilterData] = useState([]);
  useEffect(() => {
    setFilterData(foodItems)
  }, [foodItems])

  const filterResult = (selected) => {
    if (selected == 'Rice and Biryani') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Rice') || item.foodCategory.includes('Biryani')
      });
      setFilterData(result)
    }
    else if (selected == 'Paneer') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Paneer')
      })
      setFilterData(result)
    }
    else if (selected == 'Chicken') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Chicken')
      })
      setFilterData(result)
    }
    else if (selected == 'Paratha and Breads') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Paratha') || item.foodCategory.includes('Bread')
      })
      setFilterData(result)
    }
    else if (selected == 'Pizza') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Pizza')
      })
      setFilterData(result)
    }
    else if (selected == 'Pasta') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Pasta')
      })
      setFilterData(result)
    }
    else if (selected == 'Dal') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Dal')
      })
      setFilterData(result)
    }
    else if (selected == 'Soup') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Soup')
      })
      setFilterData(result)
    }
    else if (selected == 'Chinese') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Chinese')
      })
      setFilterData(result)
    }
    else if (selected == 'Dessert') {
      const result = foodItems.filter((item) => {
        return item.foodCategory.includes('Dessert')
      })
      setFilterData(result)
    }
}







    return (<div>
        <div className="searchbar_container">
            <SearchIcon className="searchBar-icon" />
            <input type='text' className="searchbar" style={{ fontSize: '25px' }}
                placeholder="Search Item..." onChange={(e) => setQuery(e.target.value)} />
        </div>

        <section className="menu-category-wapper">
      <div className='category-wrapper'>
        <h2 className="heading-category">Category</h2>
        <ListGroup defaultActiveKey="#link1">
          <ListGroup.Item className='list-default-option' href="#" >
            BestSellers
          </ListGroup.Item><a className="anchor_scroll" >
          <ListGroup.Item href="#menu_scroll" className="listItem-wrapper" onClick={() => filterResult(category[0])}>
            {category[0]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[1])}>
            {category[1]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[2])}>
            {category[2]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[3])}>
            {category[3]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[4])}>
            {category[4]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[5])}>
            {category[5]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[6])}>
            {category[6]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[7])}>
            {category[7]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[8])}>
            {category[8]}
          </ListGroup.Item>
          <ListGroup.Item className="listItem-wrapper" onClick={() => filterResult(category[9])}>
            {category[9]}
          </ListGroup.Item></a>

        </ListGroup>
      </div>
      <div className="menu-wrapper">
        <section>
          <h2 className="heading-bestseller">Best Seller</h2>
          <div className="bestSellerSection">
            <BestSeller foodItems={foodItems} />
          </div>
        </section>
        <div >
        {query ? (
            <div>
                <section  className="menu-search-wrapper">
                                <h2 className="heading-menu" >Menu</h2>
                                <div className="menu-section">
                                    <MenuSection foodItems={searchResult} />
                                </div>
                            </section>
            </div>) : <section >
                                <h2 className="heading-menu">Menu</h2>
                                <div className="menu-section">
                                    <MenuSection foodItems={filterData} />
                                </div>
                            </section>}
        </div>
      </div>
    </section>

        
    </div>
    )
}
export default Search;