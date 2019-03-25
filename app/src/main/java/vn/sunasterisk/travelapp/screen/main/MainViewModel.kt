package vn.sunasterisk.travelapp.screen.main

import vn.sunasterisk.travelapp.base.BaseViewModel
import vn.sunasterisk.travelapp.data.DataManager
import vn.sunasterisk.travelapp.utils.SchedulerProvider

class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(dataManager, schedulerProvider)