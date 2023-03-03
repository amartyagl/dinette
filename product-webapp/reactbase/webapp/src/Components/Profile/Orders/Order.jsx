// import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { Base_Url } from '../../BaseUrl';
import { Foodlist } from './FoodList';

const columns = [
  { id: 'orderId', label: 'Order ID', minWidth: "auto" },
  { id: 'foodlist', label: 'Food', minWidth: "auto" },
  { id: 'orderDate', label: 'Order Date', minWidth: "auto" },
  { id: 'value', label: 'Total Order', minWidth: "auto" },

  { id: 'orderStatus', label: 'Status', minWidth: "auto" }
];

export const Order = (props) => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [rows, setRows] = useState()
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    event.target.value ? setRowsPerPage(+event.target.value) : setRowsPerPage(0);
    setPage(0);
  };

  useEffect(() => {
    axios.get(`${Base_Url}/order-service/v1/api/orderByEmail/${props.email}`, {
      headers: {
        "Content-Type": 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Authorization': localStorage.getItem('token')
      }
    }).then(response => {
      response.data?.map(resp => {
        resp.value = `₹${resp.value}`;
        resp.orderDate = new Date(resp.orderDate).toDateString();
        return resp
      })

      setRows(response.data)
    })
  }, [props.email])

  return (
    <Paper sx={{ width: '80%', overflow: 'hidden', margin: "2rem 2rem 2rem 8rem" }} className="table-class">
      <TableContainer sx={{ maxHeight: 350, fontSize: '2rem' }} >
        <Table stickyHeader aria-label="sticky table"  >
          <TableHead >
            <TableRow >
              {columns.map((column) => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth, backgroundColor: "rgba(133, 14, 53, 0.6)", fontSize: "1.3rem", color: "#FFF5E4" }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody >
            {
              rows?.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                ?.map((row) => {
                  return (
                    <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                      {columns.map((column) => {
                        const value = (Array.isArray(row[column.id])) ? <Foodlist foodList={row[column.id]} /> : row[column.id];
                        return (
                          <TableCell key={column.id} align={column.align}>
                            {column.format && typeof value === 'number'
                              ? column.format(value)
                              : value}
                          </TableCell>
                        );
                      })}
                    </TableRow>
                  );
                })
            }
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[5, 10, 15, 20, 25, 100]}
        component="div"
        count={rows?.length ? rows?.length : 0}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>
  );
}