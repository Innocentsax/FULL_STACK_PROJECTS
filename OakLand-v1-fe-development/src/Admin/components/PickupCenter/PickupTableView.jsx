  import { useState } from 'react'
  import { pickupColumns } from './pickupTableData'
  import DrawerForm from '../Forms/DrawerForm';
  import ReactPaginate from 'react-paginate';
  import PickupCenterForm from '../Forms/PickupCenterFom';

  import {
    LoadingOutlined, 
    CaretLeftOutlined, 
    CaretRightOutlined  } from '@ant-design/icons';
  
  import { Table, Spin, Empty } from 'antd';
  import { Warehouse } from '@mui/icons-material';
  import useCategory from '../../../hooks/useCategory';
  import useProduct from '../../../hooks/useProduct';

  const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

  const PickupTableView = ({ tableTitle }) => {
    const changePage = ({ selected }) => setPageNumber(selected)
    const { pickupCenters, totalPages, setPageNumber, fetching, 
      totalElements, setSinglePickupCenter, } = useCategory()
    const { setHeaderTitle, headerTitle } = useProduct()

    const[showDrawer, setShowDrawer] = useState(false)

    const handleShowDrawer = () => {
      setHeaderTitle("Add New Pickup Center")
      setSinglePickupCenter({
              name: "",
              location: "",
              state: "",
              email: "",
              phone: "",
              delivery: "",
            })
      setShowDrawer(!showDrawer);
    }

    return (
        <section className="table-data-section">
          <DrawerForm
             title={headerTitle}
             showDrawer={showDrawer}
              setShowDrawer={setShowDrawer}
              formLayout={<PickupCenterForm
                setShowDrawer={ setShowDrawer } 
            />}
          />
          {fetching &&
            <div style={{ width: "100vw", display: "flex", height:"100%", alignItems: "center", justifyContent: "center", }}>
                <Spin indicator={antIcon} style={{ color: "rgb(218, 196, 161)" }}/>
            </div>
          }
          
            <div className="table-div">
            {
              pickupCenters?.length > 0 &&   
              <div className="title-head"> 
                <div className='title-sub-head'>
                  <button className="home-btn" onClick={handleShowDrawer}>
                    <Warehouse />Add New PickupCenter
                  </button>
                    <button className="btn-count">{totalElements}</button>
                </div>
                <h2 className='"layout-h2-header'>{tableTitle}</h2>

                <ReactPaginate 
                  previousLabel={<CaretLeftOutlined />}
                  nextLabel={<CaretRightOutlined />}
                  pageCount={totalPages} 
                  onPageChange={changePage}
                  containerClassName={"paginationBtns"}
                  previousLinkClassName={"prevBtn"}
                  nextLinkClassName={"nextBtn"}
                  disabledClassName={"paginationDisabled"}
                  activeClassName={"paginationActive"}
                />
              </div> 
            }

            {pickupCenters?.length > 0 && 
              <Table 
              dataSource={ pickupCenters }
              rowkey={ center => center.id } 
              bordered
              pagination={false}
              scroll={{ x: '400px', y: 600 }}  
              columns={pickupColumns(setShowDrawer)} 
          
        />
      }
      </div>

          { pickupCenters?.length === 0 && !fetching && 
            <div style={{ width: "100vw", display: "flex", height:"100%", 
              alignItems: "center", justifyContent: "center", }}>
              <Empty> 
                <button className="home-btn" onClick={handleShowDrawer}>
                  <Warehouse />Add New PickupCenter
                </button>
              </Empty>
            </div>
          }    
      </section>
    )
  }

  export default PickupTableView