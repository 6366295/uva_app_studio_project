Design Document
===============

The project is an autoscrolling top down shooter for Android

Classes
-------

* MainActivity
* HighScoreActivity
    * HighScoreDatabase
* GameActivity
    * GameGLSurfaceView
    * GameGLRenderer
        * PlayerModel
        * BulletModel
        * EnemyModel
        * PlayerLogic
        * BulletLogic
        * EnemyLogic
* GameOverActivity

Advanced Sketch
---------------
![Advanced sketch](https://raw.githubusercontent.com/6366295/uva_app_studio_project/master/doc/images/advancedsketch.png)

APIs, Frameworks
----------------

* Android API level 15 (4.0.3)
* OpenGL ES 2.0
* Data Storage (SQLite)
* Touchscreen

Database
--------

| Name | Score |
|------|-------|
| AAA  | 10000 |
| BBB  |  5000 |