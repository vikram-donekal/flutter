import 'package:flutter/material.dart';



class AdminPage extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return AdminPageState();
  }

}
class AdminPageState extends State<AdminPage>{

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar:AppBar(
        title: Text("Sam's Gallery"),backgroundColor: Colors.pinkAccent,
      ),
      body: Center(



        child: RaisedButton(
          onPressed: () {
            Navigator.pop(context);
          },
          child: Text('Go back!'),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: (){
            },
        tooltip:"Add a new  Todod " ,
        child: new Icon(Icons.add) ,),
    );
  }

}