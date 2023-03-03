import React, { useEffect } from 'react'
import { useState } from 'react';
import ListGroup from 'react-bootstrap/ListGroup';
import BestSeller from './BestSeller';
import MenuSection from './MenuSection';
import Search from './Search';


export const CategoryList = ({ foodItems }) => {
  
  return (<section className='fullPage-wrapper'>
    <div className="imgcontainer">
      <div className="imgcontainer_inner">
        <h2 className="Img_text">One Stop For Your Hunger</h2>
        <Search foodItems={foodItems} />
      </div>
    </div>
  </section>)
}


