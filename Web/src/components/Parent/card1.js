import React from "react";
import ProgressBar from "react-bootstrap/ProgressBar";

export default function Card1(props) {
  return (
    <>
      <div className="card mt-2">
        <div className="card-top ">
          <p className=" px-4 fs-5">{props.name}</p>
        </div>
        <div className="card-body">
          <ProgressBar striped variant="success" now={ (props.score)} />
          <p className="text-end me-3 mt-2 fs-5">{props.score}</p>
        </div>
        
      </div>
      
    </>
    
  );
  
}
