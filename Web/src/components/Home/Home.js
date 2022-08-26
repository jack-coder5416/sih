import React from "react";
import "../main/front.css";

export default function Home() {
  return (
    <>
      <div className="mid  border-bottom d-flex flex-row">
        <img
          src={require("../img/mainlogo.jpeg")}
          alt=""
          className="img-fluid px-5 pt-3"
        />
        <p className="correct ">THE SPECIAL SCHOOL</p>
      </div>

      <div className="container-fluid">
        <div className="row">
          <div className="col-md-8">
            <div className="container">
              <div className="row mt-3 border-bottom">
                <div className="col-md-8 mt-3">
                  <p className="fs-5">
                    <span className="fw-bold">Hello,</span>
                    <br />
                    Asad Iqbql{" "}
                    <span className="fw-light">
                      (III <supscript>3rd</supscript>)
                    </span>
                  </p>
                </div>
                <div className="col-md-4 profile">
                  <img src={require("../Home/img.png")} />
                </div>
              </div>

              <div className="row mt-3">
                <h4 className="text-center">Courses Detail</h4>
                <hr className="w-25 mx-auto"></hr>
              </div>
            </div>
          </div>

          <div className="col-md-4">
            <img
              src={require("../Home/prnt.webp")}
              style={{ height: "650px", width: "500px" }}
            />
          </div>
        </div>
      </div>
    </>
  );
}