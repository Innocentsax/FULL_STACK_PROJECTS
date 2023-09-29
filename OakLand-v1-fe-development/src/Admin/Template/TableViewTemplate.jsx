  import { useState } from 'react'
  import { productColumns } from './productTableData'
  import DrawerForm from '../Forms/DrawerForm'
  import ReactPaginate from 'react-paginate';
  import { ArrowLeftTwoTone, ArrowRightAltOutlined,  } from '@mui/icons-material';
  import AddProductForm from '../Forms/AddProductForm'

  import {
    UserAddOutlined,
    LoadingOutlined
  } from '@ant-design/icons';
  
  import { Table, Spin, Empty } from 'antd';
  const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

  const TableView = ({ tableTitle }) => {
    const changePage = ({ selected }) => setPageNumber(selected)
    const[showDrawer, setShowDrawer] = useState(false)

    const handleShowDrawer = () => {
      setHeaderTitle("Add New Product")
      setShowDrawer(!showDrawer);
    }

    return (
        <section className="table-data-section">
          <DrawerForm
            title={headerTitle}
            showDrawer={showDrawer}
            setShowDrawer={setShowDrawer}
            formLayout={<AddProductForm 
                setShowDrawer={ setShowDrawer } 
              />}
          />
          {fetching ? 
            <div style={{ width: "100vw", display: "flex", height:"100%", alignItems: "center", justifyContent: "center", }}>
                <Spin indicator={antIcon} style={{ color: "rgb(218, 196, 161)" }}/>
            </div>
            :
            <Table 
            // dataSource={ products }
            // rowkey={ product => product.id } 
            bordered
            pagination={false}
            scroll={{ x: '400px', y: 600 }}  
            columns={productColumns} 
            title={() => 
              <div className="title-head"> 
                <div className='title-sub-head'>
                  <button className="home-btn" onClick={handleShowDrawer}>
                    <UserAddOutlined />Add New Product
                  </button>
                    <button className="btn-count">{totalElements}</button>
                </div>
                <h2 className='"layout-h2-header'>{tableTitle}</h2>

                <ReactPaginate 
                  previousLabel={<ArrowLeftTwoTone />}
                  nextLabel={<ArrowRightAltOutlined />}
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
        />
          }

          { products?.length === 0 && !fetching && <Empty /> }
        </section>
    )
  }

  export default TableView