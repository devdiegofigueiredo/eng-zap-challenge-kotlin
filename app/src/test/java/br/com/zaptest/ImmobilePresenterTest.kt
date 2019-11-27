package br.com.zaptest

import br.com.zaptest.entities.Immobile
import br.com.zaptest.ui.ImmobileContract
import br.com.zaptest.ui.ImmobilePresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ImmobilePresenterTest {

    @Mock
    lateinit var view: ImmobileContract.View

    @Mock
    lateinit var interactor: ImmobileContract.Interactor

    private lateinit var presenter: ImmobilePresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = ImmobilePresenter(view, interactor)
    }

    @Test
    fun fetchImmobilesSuccessTest() {
        val captor = com.nhaarman.mockitokotlin2.argumentCaptor<ImmobileContract.Presenter.ImmobilesCallback>()

        presenter.fetchImmobiles()
        Mockito.verify(view).hideErrorScreen()
        Mockito.verify(view).showLoading()
        Mockito.verify(interactor).fetchImmobiles(captor.capture())

        captor.firstValue.onImmobilesSuccess()
        Mockito.verify(view).setupImmobilesSlider()

        captor.firstValue.onVivaRealImmobilesSuccess(Mockito.anyList<Immobile>())
        Mockito.verify(view).setupVivaRealImmobiles(Mockito.anyList<Immobile>())

        captor.firstValue.onZapImmobilesSuccess(Mockito.anyList<Immobile>())
        Mockito.verify(view).setupZapImmobiles(Mockito.anyList<Immobile>())
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showTabs()
    }

    @Test
    fun fetchImmobilesErrorTest() {
        val captor = com.nhaarman.mockitokotlin2.argumentCaptor<ImmobileContract.Presenter.ImmobilesCallback>()

        presenter.fetchImmobiles()
        Mockito.verify(view).hideErrorScreen()
        Mockito.verify(view).showLoading()
        Mockito.verify(interactor).fetchImmobiles(captor.capture())

        captor.firstValue.onImmobilesError("Erro")
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showErrorScreen()
        Mockito.verify(view).displayFastMessage("Erro")
    }

}