import React, {useState} from 'react'
import {Button, Grid, TextField} from "@mui/material";
import {Add, PlusOne} from "@mui/icons-material";

const AddTodo = (props) => {
  const [item, setItem] = useState({
    title: ""
  });
  const addItem = props.addItem;

  const onInputChange = (e) => {
    setItem({title: e.target.value});
    console.log(item)
  }

  const onButtonClick = () => {
    if(item.title !== '') addItem(item);
    setItem({title: ""})
  }

  const enterKeyEventHandler = (e) => {
    if (e.key == 'Enter') onButtonClick();
  }
  return (
     <Grid container style={{marginTop: 20}}>
       <Grid xs={11} md={11} item style={{paddingRight: 16}}>
         <TextField placeholder='Add Todo here' fullWidth
                    onChange={onInputChange}
                    onKeyUp={enterKeyEventHandler}
                    value={item.title}/>
       </Grid>
       <Grid xs={1} md={1} item>
         <Button fullWidth style={{height: '100%'}} variant={"outlined"}
                 onClick={onButtonClick}
                 ><Add></Add></Button>
       </Grid>
     </Grid>
  )
}
export default AddTodo
