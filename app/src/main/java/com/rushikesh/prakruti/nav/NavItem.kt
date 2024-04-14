package com.rushikesh.prakruti.nav

import com.dyneshwari.nav.Item


sealed class NavItem {
    object FinalResult :
        Item(
            path = NavPath.FINALRESULT.toString(),
        )

    object UserData :
        Item(
            path = NavPath.USERINFO.toString(),
        )

    object Qna :
        Item(
            path = NavPath.QNA.toString(),
        )

}

sealed class NavigationAction {
    object NavigateToQna : NavigationAction() // Navigate to Qna screen
    object PopUpToRoot : NavigationAction() // Pop up to the root destination
//   optional route to pop up to (if not specified, pops up to the nearest ancestor with this class)
}

