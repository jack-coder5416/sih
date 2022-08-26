import React from "react";
import "./front.css";

export default function front() {
  return (
    <>
    <>
      <>
      <div class="d-flex justify-content-start header-color-re">
          <img
            src={require("../img/mainlogo.png")}
            alt="" 
           className="logo-size-re img-fluid "
          />
          <div className="d-flex justify-content-evenly"><p className="correct">THE SPECIAL SCHOOL</p></div>
          <div className="d-flex justify-content-xl-end ">
                </div>
        </div>
        <div id="head" className="pt-5">
          <div className="container ">
            <div className="row mt-5">
              <div className="col-sm-12 col-md-4 col-lg-4 col-12">
                <h3 className="text-white fw-bold">Education Program Cell</h3>
                <h4 className="text-white fw-bold">
                  Education Development Program(TSs) <br /> 
                  To Know The Details
                </h4>
                <button className="btn  mt-3">
                  <a className="text-white  " href="/login">Login Now</a>
                </button>
              </div>
            </div>
          </div>
        </div>
        <div id="history">
          <div className="container mt-5">
            <h2 className="text-center mt-5">About Us</h2>
            <hr className="w-25 m-auto" />
            <p className="mt-5 fs-4">
            The Special School is an application based learning plateform with assistive technology where specially abled peoples suffering from dyslexia , ASD , impaired speech hearing and sight are prioritised to ease their learning by using Mindmaps , Podcasts , Games and Animations.

            Our motive is to reach every people across India and can setup a plateform where student can learn and grow together not only with the subject skill but also with their soft skills. 
            
            Our goal is to take literacy rate to 100% in India where not only the physically abled but also to specially abled person , so they can all compete at same level and with same confidence.
            </p>
          </div>
        </div>
        <div id="galler">
          <div className="container">
            <div className="row">
              <div className=" offset-3 col-6 mt-5">
                <h3 className="text-center">Gallery</h3>
                <hr className="w-25 m-auto" />
                <div
                  id="carouselExampleCaptions"
                 className="carousel slide mt-5"
                  data-bs-ride="carousel"
                >
                  <div className="carousel-indicators">
                    <button
                      type="button"
                      data-bs-target="#carouselExampleCaptions"
                      data-bs-slide-to={0}
                     className="active"
                      aria-current="true"
                      aria-label="Slide 1"
                    />
                    <button
                      type="button"
                      data-bs-target="#carouselExampleCaptions"
                      data-bs-slide-to={1}
                      aria-label="Slide 2"
                    />
                    <button
                      type="button"
                      data-bs-target="#carouselExampleCaptions"
                      data-bs-slide-to={2}
                      aria-label="Slide 3"
                    />
                  </div>
                  <div className="carousel-inner">
                    <div className="carousel-item active">
                      <img
                        src={require("../img/Carousal2.jpg")}
                       className="d-block w-100 h-60"
                        alt="..."
                      />
                     
                    </div>
                    <div className="carousel-item">
                      <img
                        src={require("../img/Carousal3.jpg")}
                       className="d-block w-100 h-60"
                        alt="..."
                      />
                      
                    </div>
                    <div className="carousel-item">
                      <img
                        src={require("../img/m3.webp")}
                       className="d-block w-100 h-0"
                        alt="..."
                      />
                     
                    </div>
                   
                  </div>
                  <button
                   className="carousel-control-prev"
                    type="button"
                    data-bs-target="#carouselExampleCaptions"
                    data-bs-slide="prev"
                  >
                    <span
                     className="carousel-control-prev-icon"
                      aria-hidden="true"
                    />
                    <span className="visually-hidden">Previous</span>
                  </button>
                  <button
                   className="carousel-control-next"
                    type="button"
                    data-bs-target="#carouselExampleCaptions"
                    data-bs-slide="next"
                  >
                    <span
                     className="carousel-control-next-icon"
                      aria-hidden="true"
                    />
                    <span className="visually-hidden">Next</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        
      </>
      
    </>
    <div className="footer">
    <div className="card-body text-center">
          <i className="fa-brands mt-4 py-2 fa-facebook  " />
          <i className="fa-brands mt-4  px-3 fa-twitter  " />  
          <h5 className="mt-3 card-title copy-color">@Copyright 2022 The Special School.</h5>
</div>
</div>
</>
  );
}
