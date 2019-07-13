import 'package:flutter/material.dart';



class GuestPage extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return GuestPageState();
  }

}
class GuestPageState extends State<GuestPage>{

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
    );
  }

}