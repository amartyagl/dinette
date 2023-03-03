import React, { useState } from 'react'
import MenuModal from './MenuModal';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import '../Style/MenuCard.css';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import SearchIcon from '@mui/icons-material/Search';
 
import {Base_Url} from '../Components/BaseUrl'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
function MenuCard() {
  const [show, setShow] = React.useState(false);
  const [data, setData] = React.useState([]);
  const [passData, setPassData] = React.useState([]);
  const [menuData, setMenuDAta] = React.useState([]);
  const [menuCardData, setMenuCardData] = React.useState([]);
  const [rice, setRice] = React.useState(false);
  const [biryani, setBiryani] = React.useState(false);
  const [veg, setVeg] = React.useState(false);
  const [Nveg, setNveg] = React.useState(false);
  const [all, setAll] = React.useState(true);
  const [week, setweek] = React.useState([])
  // const alert = useAlert()


  // Weeks days api
  React.useEffect(() => {

    setweek(
      [
        {
          "id": 1,
          "Day": "MONDAY",
          "weekDayImg": "https://i.postimg.cc/hjB6Wz5b/monday00.png"
        },
        {
          "id": 2,
          "Day": "TUESDAY",
          "weekDayImg": "https://i.postimg.cc/Hsvt7t0v/tue1-transformed.jpg"
        },
        {
          "id": 3,
          "Day": "WEDNESDAY",
          "weekDayImg": "https://i.postimg.cc/5yyVNVM2/wed1-transformed.jpg"
        },
        {
          "id": 4,
          "Day": "THURSDAY",
          "weekDayImg": "https://i.postimg.cc/tC3zwvHf/thusday00.png"
        },
        {
          "id": 5,
          "Day": "FRIDAY",
          "weekDayImg": "https://i.postimg.cc/QxDzbDz9/fri1-transformed.jpg"
        },
        {
          "id": 6,
          "Day": "SATURDAY",
          "weekDayImg": "https://i.postimg.cc/tgZWPQRY/sat1-transformed.jpg"
        },
        {
          "id": 7,
          "Day": "SUNDAY",
          "weekDayImg": "https://i.postimg.cc/4NqcxzQG/sunday00.png"
        }
      ]
    )
  }, []);

  // Weeks days menu card api
  React.useEffect(() => {
    // console.log(Base_Url,'base url link')
    const userDatail = localStorage.getItem('userDetails')
    const email = JSON.parse(userDatail).email
    // const email = 'riya@gmail.com'
    console.log(email)
    // const url = 'http://localhost:5000/TimeTable';
    // const url = `http://localhost:8080/menu-service/v2/api/timeTable/${email}`;
    const url = `${Base_Url}/menu-service/v2/api/timeTable/${email}`;

    axios.get(url,{ headers: {
      "Content-Type": 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization':  localStorage.getItem('token') 
    }})
      .then((response) => {
        setData(response.data)
        console.log(response.data, 'current user responsed')
      })
      .catch((error) => {
        console.log(error)
        console.log(error.response.data.errorMessage, 'new user')
        if (error.response.data.errorMessage == "No Table scheduled yet.") {
          createNewUser()
          window.location.reload(false)
        }

      });
  }, []);

  const createNewUser = () => {
    // const url = `http://localhost:8080/menu-service/v2/api/timeTable`;
    const url = `${Base_Url}/menu-service/v2/api/timeTable`;
    const userDatail = localStorage.getItem('userDetails')
    const email = JSON.parse(userDatail).email;
    const name = JSON.parse(userDatail).name
    // const email = 'riya@gmail.com'
    // const name = 'riya'
    const menuCardItem = {
      "MONDAY":[],
      "TUESDAY":[],
      "WEDNESDAY":[],
      "THURSDAY":[],
      "FRIDAY":[],
      "SATURDAY":[],
      "SUNDAY":[]
    }
    const newUser = {
      emailId: email,
      firstName:name,
      menuItems: menuCardItem

    }
    fetch(url, {
      method: 'POST',
      headers: {
        "Content-Type": 'application/json',
        'Authorization':  localStorage.getItem('token'),
        
      },
      body: JSON.stringify(newUser)
    })

      .then(response => {
        console.log('response', response);
        if (response.status === 201) {

          console.log('time table created for', email)
        }
      }).catch(e => {
        console.log(e)
      })



  }
  // menu services item list
  React.useEffect(() => {
    // const url = 'http://localhost:5000/MenuService';
    const url = `${Base_Url}/menu-service/v1/api/menu`;

    // const url = 'http://localhost:8080/menu-service/v1/api/menu';
    axios.get(url, { headers: {
      "Content-Type": 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization':  localStorage.getItem('token') 
    }})
      .then((response) => {
        setMenuDAta(response.data)
        setMenuCardData(response.data)

      })
      .catch((error) => console.log(error));
  }, []);

  // filter the menu data accordint to category 
  const filterResult = (catItem) => {
    if (catItem == "Rice") {
      setRice(true);
      setBiryani(false);
      setVeg(false);
      setNveg(false);
      setAll(false);
    } else if (catItem == "Veg") {
      setRice(false);
      setBiryani(false);
      setVeg(true);
      setNveg(false);
      setAll(false);
    } else if (catItem == "NonVeg") {
      setRice(false);
      setBiryani(false);
      setVeg(false);
      setNveg(true);
      setAll(false);
    } else if (catItem == "Biryani") {
      setRice(false);
      setBiryani(true);
      setVeg(false);
      setNveg(false);
      setAll(false);
    } else {
      setRice(false);
      setBiryani(false);
      setVeg(false);
      setNveg(false);
      setAll(true);
    }
    if (catItem != 'All') {
      const result = menuCardData.filter((item) => {

        return item.foodCategory.includes(catItem);
      });
      setMenuDAta(result);

    } else {
      setMenuDAta(menuCardData);
    }

  }

  const handleChange = (event) => {
    const searchValue = event.target.value
    if (searchValue != '') {
      const filterCard = menuCardData.filter((data) => {
        if (data.foodDescription.toLowerCase().includes(searchValue.toLowerCase())) {
          return data
        }
        else if (data.foodName.toLowerCase().includes(searchValue.toLowerCase())) {
          return data
        }
      });
      setMenuDAta(filterCard)
    } else {
      setMenuDAta(menuCardData)
    }
  }

  // send the click week table data
  const temp = (e) => {
    setPassData(e)
    setShow(true)
  }

  // save the card value in week table
  const handleSave = (day, cardData, userData) => {
    const wkDay = day.Day
    const email = userData.emailId
    const id = userData.timetableId
    const changeValue = userData?.menuItems
    const menuCompare = []
    Object.entries(userData?.menuItems)?.map((menu) => {
      menuCompare.push(menu[0])

    })
    if (menuCompare.includes(wkDay)) {
      changeValue[`${day.Day}`].push(cardData);
    } else {
      changeValue[`${day.Day}`] = [cardData]
    }
    const data = {
      timetableId: id,
      emailId: userData.emailId,
      name: userData.name,
      menuItems: changeValue
    }
    console.log(data, 'daata final')
    let requestOptions = {
      method: 'PUT',
      headers: {
        "Content-Type": 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Authorization':  localStorage.getItem('token') 
      },
      body: JSON.stringify(data)
    };
    fetch(`${Base_Url}/menu-service/v2/api/timeTableByEmail/${email}`, requestOptions)
      .then(response => {
        if (response.ok) {
          
          toast.success(` ${cardData.foodName} added in your ${wkDay}`)
          setShow(false)
        }
      }).catch((e) => {
        console.log(e)
      })
  }

  // delete the item form week day table
  const deleteItem = (day, menuItems, deleteItem, userData) => {
    console.log(userData, 'user data !!!')
    const id = userData.timetableId
    const c = deleteItem.foodItemId
    const email = userData.emailId
    let remainingArr = []
    let modifiedData = []
    Object.entries(userData?.menuItems)?.map((menu) => {
      if (menu[0] == day) {
        remainingArr = (menu[1]).filter(del => del.foodItemId != deleteItem.foodItemId)
        menu[0] = remainingArr
      }

    })

    userData.menuItems[`${day}`] = remainingArr;
    console.log(userData.menuItems[`${day}`])
    modifiedData = userData.menuItems
    const changeValue = modifiedData
    const data = {
      timetableId: id,
      emailId: email,
      name: userData.name,
      menuItems: changeValue
    }
    let requestOptions = {
      method: 'PUT',
      headers: {
        "Content-Type": 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Authorization':  localStorage.getItem('token') 
      },
      body: JSON.stringify(data)
    };
    fetch(`${Base_Url}/menu-service/v2/api/timeTableByEmail/${email}`, requestOptions)
      .then(response => {
        if (response.ok) {
          console.log('update successfully')
          toast.error(` ${deleteItem.foodName} deleted from ${day}`)
          window.location.reload(false);
        }
      }).catch((e) => {
        console.log(e)
      })

  }


  return (
    <div className='content '>
      <div className='sub-header text-center py-4'>
        <h2>Time Table</h2>
      </div>
      <div className="py-4 py-lg-5 container">
        <div className='row justify-content-center align-item-center'>
          {
            week.map((weekDay, dayName) => {
              return (
                <div className='col-11 col-md-6 col-lg-4 mx-0 mb-4' style={{ width: "25rem" }}>
                  <div className="card p-0 overflow-hidden h-100 shodo" style={{ backgroundColor: 'rgba(255, 196, 196, 0.5)' }}>
                    <div className="h1 text-center card-header time-table-img">
                      <img src={weekDay.weekDayImg} className="card-img-top" alt="..." />
                    </div>
                    <div className="card-body">
                      {Object.keys(data).length > 0 ?
                        Object.entries(data?.menuItems)?.map((menu) => {
                          if (menu[0] == weekDay.Day) {
                            return (
                              <Table bordered>
                                <thead className='d-none col-11 col-md-6 col-lg-3'>
                                  <tr>
                                    <th colSpan={2}>Image</th>
                                    <th>Action</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  {menu[1].length > 0 ?
                                    menu[1].map((menuDataList, index) => {
                                      return (
                                        <tr key={index}>
                                          <td ><img src={menuDataList?.foodPicture} width="84px" height="82px" /></td>
                                          <td className='p-4'>{menuDataList?.foodName}</td>
                                          <td className='p-4'><button className='btn'><DeleteIcon onClick={() => { deleteItem(weekDay.Day, menu[1], menuDataList, data) }} /></button></td>
                                        </tr>
                                      )
                                    }) : null
                                  }
                                </tbody>
                              </Table>
                            )
                          }
                        }) : null

                      }
                    </div>
                    <button className="btn btn-color" onClick={() => temp(weekDay)}>
                      <AddIcon sx={{ fontSize: 40 }} />
                    </button>
                  </div>
                  <MenuModal show={show} onClose={() => setShow(false)} passData>
                    <div className='container-flex mx-2 mt-4'>
                      <h3 className="float-end"><i className="fa-solid fa-xmark  close_btn" onClick={() => setShow(false)}></i></h3>
                      <div className='text-center mt-5 pt-5'>
                        <h2 style={{ color: '#860632' }}>ADD MENU FOR {passData.Day} </h2>
                      </div>
                      <div className='row mx-2 '>
                        <div class="form-group has-search  col-md-6  mx-auto p-1 p-3 mb-5  round-100  search-bar">
                          <span class="fa fa-search form-control-feedback"></span>
                          <input type="text" class="form-control b-none" placeholder="Search" onChange={handleChange} autocomplete="off" />
                        </div>

                        <div className='col-lg-12 col-md-12 col-12 '>
                          <div className="row  justify-content-evenly">
                            <div role="button" className={all ? 'col-md-2 text-center pt-2 rounded  btn-color-active   mb-3 col-md-2 ' : 'btn btn-color mb-3 col-md-2 '} id="All" onClick={() => filterResult('All')}>All</div>
                            <div role="button" className={rice ? 'col-md-2 text-center pt-2 rounded btn-color-active   mb-3 col-md-2 ' : 'btn  mb-3 col-md-2  '} id="Rice" onClick={() => filterResult('Rice')}>Rice</div>
                            <div role="button" className={biryani ? 'col-md-2  text-center pt-2 rounded btn-color-active  mb-3 col-md-2 ' : 'btn btn-color mb-3 col-md-2  '} id="Biryani" onClick={() => filterResult('Biryani')}>Biryani</div>
                            <div role="button" className={veg ? 'col-md-2 text-center pt-2 rounded  btn-color-active   mb-3 col-md-2 ' : 'btn btn-color mb-3 col-md-2 '} id="Veg" onClick={() => filterResult('Veg')}>Veg</div>
                            <div role="button" className={Nveg ? 'col-md-2 text-center pt-2 rounded  btn-color-active  mb-3 col-md-2 ' : 'btn btn-color mb-3 col-md-2 '} id="NonVeg" onClick={() => filterResult('NonVeg')}>Non Veg</div>
                          </div>
                        </div>


                        <div className='col-md-12 item_card display-scroll'>
                          <div className='row mb-2'>
                            {menuData ?

                              menuData.map((menuDataList) => {
                                // const { id, foodPicture, foodName, foodAvailability, foodCategory, foodPrize, foodDescription } = menuDataList;
                                let description100 = (menuDataList?.foodDescription).substring(0, 88)
                                return (
                                  <div className='col-lg-4 col-md-6 col-12  mt-5 mb-1 mx-2shadow-sm'>
                                    <div className='card p-2 ' style={{ backgroundColor: 'rgba(255, 196, 196, 0.5)' }}>
                                      <div className='row'>
                                        <div className='col-lg-4 col-xl-4 col-md-4 col-sm-4 col-4 justify-content-evenly'>
                                          <div className="w-100 ">
                                            <img src={menuDataList.foodPicture} width="150px" height="110px" style={{ paddingRight: '2.5rem' }} alt="Card image cap" className='rounded pt-2' />
                                          </div>
                                        </div>

                                        <div className="col-md-8 col-8">
                                          <div className="row">
                                            <h3>{menuDataList.foodName}</h3>
                                            <p>{description100} .</p>
                                          </div>
                                          <div className="row justify-content-evenly">
                                            <div className="col-lg-7 col-md-7 col-6 col-sm-6 col-xs-6">
                                              <p>₹ {menuDataList.foodSize.small}</p>
                                            </div>
                                            <div className="col-lg-5 col-md-5 col-6 col-sm-6 col-xs-4">
                                              <button className="btn btn-color" onClick={() => { handleSave(passData, menuDataList, data) }}>Add</button>
                                            </div>
                                          </div>
                                        </div>

                                      </div>
                                    </div>

                                  </div>
                                )
                              }) : <h4>Match Not found</h4>}

                          </div>

                        </div>

                        {/* </div> */}
                      </div>
                    </div>
                  </MenuModal>
                </div>
              )
            })
          }
        </div>
      </div>
    </div>

  )
}

export default MenuCard
