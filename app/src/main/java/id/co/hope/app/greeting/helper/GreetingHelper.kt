package id.co.hope.app.greeting.helper

import id.co.hope.app.greeting.model.GreetingModel

class GreetingHelper {
    companion object{
        fun greetingData(): MutableList<GreetingModel>{
            var greetingList: MutableList<GreetingModel> = ArrayList()
            var greetingModel = GreetingModel("Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            greetingList.add(greetingModel)

            greetingModel = GreetingModel("Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            greetingList.add(greetingModel)

            return greetingList
        }
    }
}