import StartFirebase from "./RFirebase";
import React from "react";
import { useNavigate } from "react-router-dom";
import { ref, onValue, child } from "firebase/database";
import Card from "./Card";
import Card1 from "./card1";
import { auth } from "../../firebase";
import "./PI.css";
const db = StartFirebase();

export class RealtimeData extends React.Component {
  constructor() {
    super();
    this.state = {
      tableData: [],
      name: "",
      class: "",
      email: "",
      progress: [],
      score: [],
    };
  }
  componentDidMount() {
    const dbRef = ref(db, "Children");
    onValue(dbRef, (snapshot) => {
      let records = [];
      snapshot.forEach((childSnapshot) => {
        let keyName = childSnapshot.key;
        let data = childSnapshot.val();
        records.push({ key: keyName, data: data });
      });
      this.setState({ tableData: records });
    });
  }
  handleSignOut() {
    auth
      .signOut()
      .then(() => {
        alert("You are loggedOut");
      })
      .catch((e) => {
        console.log("Error signing out", e);
      });
  }

  render() {
    return (
      <>
        {/* <div className="mid  border-bottom d-flex flex-row">
          <img
            src={require("../img/mainlogo.png")}
            alt=""
            className="img-fluid px-5 pt-3"
          />
          <p className="correct ">THE SPECIAL SCHOOL</p>
          <div className="mt-5 ms-auto me-5">
           <a href="/"> <button className="btn btn-primary mx-2" >Home</button></a>
            <a href="/"><button className="btn btn-primary mx-2" onClick={this.handleSignOut} >Log Out</button></a>
          </div>
        </div> */}

        <div class="d-flex justify-content-start header-color-re">
          <img
            src={require("../img/mainlogo.png")}
            alt=""
            className="logo-size-re img-fluid "
          />
          <div className="d-flex justify-content-evenly">
            <p className="correct">THE SPECIAL SCHOOL</p>
          </div>
          <div className="d-flex justify-content-xl-end "></div>
          <div className="mt-3 ms-auto me-5">
            <a href="/">
              {" "}
              <button className="btn btn-primary mx-2">Home</button>
            </a>
            <a href="/">
              <button
                className="btn btn-primary mx-2"
                onClick={this.handleSignOut}
              >
                Log Out
              </button>
            </a>
          </div>
        </div>

        {this.state.tableData.map((row, index) => {
          if (row.data.Email == this.props.email) {
            let y = "";
            let z = "";
            let x =
              parseInt(row.data.Score.EnglishLanguage) +
              parseInt(row.data.Score.EnglishLiterature) +
              parseInt(row.data.Score.Hindi) +
              parseInt(row.data.Score.GeneralKnowledge) +
              parseInt(row.data.Score.Mathematics) +
              parseInt(row.data.Score.Environmental);
            if (x>=0 && x / 6 <= 40) {
              // y = "Poor";
              z = "The Student need special care , do some real life based activities";
            } else if (x / 6 > 40 && x / 6 <= 70) {
              // y = "Fair";
              z =
                "Student performance is good need daily practice for understanding each and every thing.";
            } else if(x/6 >70 && x/6<=100) {
              // y = "Good";
              z =
                "Student is excellent , practice need on a dily basis to improve more.";
            } 
            return (
              <>
                <div className="container-fluid prentimg">
                  <div className="row">
                    <div className="col-md-7 mx-auto ">
                      <div className="container me-5">
                        <div className="row mt-3 border-bottom">
                          <div className="col-md-8 mt-3 ">
                            <p className="fs-5 text-white">
                              <span className="fw-bold text-white">Hello,</span>
                              <br />
                              <span className="fw-bold text-white">
                                Name : {row.data.Name}
                              </span>
                              <br />
                              <span className="fw-bold text-white">
                                Class :{" "}
                              </span>
                              <span className="fw-bold text-white">
                                {row.data.Class}
                              </span>
                            </p>
                          </div>
                          {/* <div className="col-md-4 profile">
                              <img src={row.data.Image} />
                            </div> */}
                        </div>

                        <div className="row mt-3">
                          <h4 className="fw-bold text-center">
                            Courses Detail
                          </h4>
                          <hr className="w-25 mx-auto"></hr>
                          <Card
                            name={"English Language"}
                            progress={
                              (row.data.Progress.EnglishLanguage / 10) * 100
                            }
                          />
                          <Card
                            name={"English Literature"}
                            progress={(
                              (row.data.Progress.EnglishLiterature / 20) * 100).toPrecision(2)
                            }
                          />
                          <Card
                            name={"Environmental"}
                            progress={(
                              (row.data.Progress.Environmental / 24) * 100).toPrecision(2)}
                          />
                          <Card
                            name={"General Knowledge"}
                            progress={(
                              (row.data.Progress.GeneralKnowledge / 15) * 100).toPrecision(2)
                            }
                          />
                          <Card
                            name={"Hindi"}
                            progress={(
                              (row.data.Progress.Hindi / 14) * 100).toPrecision(2)}
                          />
                          <Card
                            name={"Mathematics"}
                            progress={(
                              (row.data.Progress.Mathematics / 14) * 100).toPrecision(2)
                            }
                          />
                        </div>

                        <div className="row mt-3">
                          <h4 className="fw-bold text-center">Courses Score</h4>
                          <hr className="w-25 mx-auto"></hr>
                          <Card1
                            name={"English Language"}
                            score={((row.data.Score.EnglishLanguage/100)*100).toPrecision(2)}
                          />
                          <Card1
                            name={"English Literature"}
                            score={((row.data.Score.EnglishLiterature/200)*100).toPrecision(2)}
                          />
                          <Card1
                            name={"Environmental"}
                            score={((row.data.Score.Environmental/480)*100).toPrecision(2)}
                          />
                          <Card1
                            name={"General Knowledge"}
                            score={((row.data.Score.GeneralKnowledge/300)*100).toPrecision(2)}
                          />
                          <Card1 name={"Hindi"} score={((row.data.Score.Hindi/280)*100).toPrecision(2)} />
                          <Card1
                            name={"Mathematics"}
                            score={((row.data.Score.Mathematics/280)*100).toPrecision(2)}
                          />
                          <div className="card mt-2 bg-dark">
                            <div className="card-top text-white">
                              <p className=" px-4 fs-5 text-center fw-bold ">
                                Recommendation
                              </p>
                              <hr className="w-25 mx-auto fw-bold fs-4" />
                            </div>
                            <div className="card-body text-white">
                              <p className="fs-4">Average Score: {(x/6).toPrecision(2)}</p>
                              <br />
                              <p className="fs-4">{z}</p>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="footer ">
                  <div className="card-body text-center">
                    <i className="fa-brands mt-4 py-2 fa-facebook text-white " />
                    <i className="fa-brands mt-4  px-3 fa-twitter text-white " />
                    <h5 className="mt-3 card-title copy-color">
                      @Copyright 2022 The Special School.
                    </h5>
                  </div>
                </div>
                </div>
              </>
            );
          }
        })}
      </>
    );
  }
}
