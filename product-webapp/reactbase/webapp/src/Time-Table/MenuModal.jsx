import styles from "../Style/MenuModal.css";
import "bootstrap/dist/css/bootstrap.min.css";
import React, { Component, useState } from "react";
import { render } from "react-dom";
import SlidingPane from "react-sliding-pane";
import "react-sliding-pane/dist/react-sliding-pane.css";

const MenuModal = ({ show, onClose, children }) => {
  const [state, setState] = useState({
    isPaneOpen: false,
    isPaneOpenLeft: false,
  });

  if (!show) {
    return null;
  }
  
  const divStyle = {
    border: '1px solid #860632',
    width: '80%',
    position: 'relative'
  };

  return (
    
    <div  className="bg-blur">
      <SlidingPane
        closeIcon={<div>Some div containing custom close icon.</div>}
        isOpen={show}
        from="bottom"
        width="80%"
        className="cardModal justify-content-center"
        style={divStyle}
        onRequestClose={() => setState({ isPaneOpenLeft: onClose })}
      >
        {children}   
      </SlidingPane>
    </div>
  );
};

export default MenuModal;
