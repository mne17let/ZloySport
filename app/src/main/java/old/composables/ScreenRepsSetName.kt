package old.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zloysport.R
import old.ui.CommonViewModel
import old.composables.common.CommonConfirmButton
import old.composables.common.CommonTextField
import old.composables.common.CommonTitleBar
import old.ui.theme.InfoSize
import old.ui.util.AMOUNT_OF_SETS

@Composable
fun ScreenEnterTrainingName(
    viewModel: CommonViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        CommonTitleBar(
            leftIconResId = R.drawable.ic_back,
            title = stringResource(id = R.string.create_drill_title),
            rightIconResId = R.drawable.ic_close,
        )
        ContentEnterRepsName()
        CommonConfirmButton(
            textResourceId = R.string.next_button_title,
            onClick = {
                navController.navigate(AMOUNT_OF_SETS)
            }
        )
    }
}

@Composable
private fun ContentEnterRepsName() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.enter_drill_name),
            fontSize = InfoSize
        )

        CommonTextField(
            labelResource = R.string.enter_drill_name_label
        )
    }
}